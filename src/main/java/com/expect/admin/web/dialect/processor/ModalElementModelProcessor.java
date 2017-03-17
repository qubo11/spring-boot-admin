package com.expect.admin.web.dialect.processor;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.AbstractElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * modal封装的标题：<ex:modal><ex:body/><ex:footer/></ex:modal>
 * 
 * 具体属性详见以下ModalAttribute
 */
public class ModalElementModelProcessor extends AbstractElementModelProcessor {

	public ModalElementModelProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, "modal", true, null, false, 200000);
	}

	@Override
	protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		ModalAttribute modalAttribute = new ModalAttribute();
		final IModelFactory modelFactory = context.getModelFactory();
		IModel modalModel = null, bodyModel = null, footerModel = null;
		int n = model.size();
		while (n-- != 0) {
			final ITemplateEvent event = model.get(n);
			// 处理body/footer内容
			boolean isFlag = false;
			String eventStr = event.toString().replace(" ", "");
			if (eventStr.contains("<ex:footer/>") || eventStr.contains("<ex:body/>")) {
				modalAttribute.content.delete(0, modalAttribute.content.length());
			}
			if (eventStr.contains("<ex:footer") || eventStr.contains("<ex:body")) {
				modalAttribute.isStart = false;
			}
			if (eventStr.equals("</ex:footer>") || eventStr.contains("</ex:body>")) {
				modalAttribute.isStart = true;
				isFlag = true;
				modalAttribute.content.delete(0, modalAttribute.content.length());
			}
			if (!isFlag && modalAttribute.isStart) {
				modalAttribute.content.insert(0, event.toString());
			}
			if (isFlag && modalAttribute.isStart) {
				isFlag = false;
			}
			if (event instanceof IProcessableElementTag) {
				IProcessableElementTag tag = ((IProcessableElementTag) event);
				String tagName = tag.getElementCompleteName();
				if ("ex:body".equals(tagName)) {
					// 创建modal-body
					bodyModel = createBody(modelFactory, modalAttribute);
				} else if ("ex:footer".equals(tagName)) {
					String close = tag.getAttributeValue("close");
					modalAttribute.footerCloseButton = close;
					// 创建modal-footer
					footerModel = createFooter(modelFactory, modalAttribute);
				} else if ("ex:modal".equals(tagName)) {
					String id = tag.getAttributeValue("id");
					String title = tag.getAttributeValue("title");
					String width = tag.getAttributeValue("width");
					String height = tag.getAttributeValue("height");
					String animation = tag.getAttributeValue("animation");
					modalAttribute.id = id;
					modalAttribute.animation = animation;
					modalAttribute.title = title;
					modalAttribute.width = width;
					modalAttribute.height = height;
					// 创建modal
					modalModel = createModal(modelFactory, modalAttribute, bodyModel, footerModel);
				}
			}
			model.remove(n);
		}

		if (modalModel != null) {
			model.addModel(modalModel);
		}
	}

	private IModel createModal(IModelFactory modelFactory, ModalAttribute modalAttribute, IModel bodyModel,
			IModel footerModel) {
		Map<String, String> modalAttrs = new HashMap<>();
		if (modalAttribute.animation != null) {
			modalAttrs.put("class", "modal " + modalAttribute.animation);
		} else {
			modalAttrs.put("class", "modal fade");
		}
		if (modalAttribute.id != null) {
			modalAttrs.put("id", modalAttribute.id);
		}
		if (modalAttribute.width != null) {
			modalAttrs.put("data-width", modalAttribute.width);
		}
		if (modalAttribute.height != null) {
			if (modalAttribute.height.contains("%")) {
				modalAttrs.put("style", "height:" + modalAttribute.height);
			} else {
				modalAttrs.put("style", "height:" + modalAttribute.height + "px");
			}
		}
		IModel modalModel = modelFactory.createModel();
		modalModel.add(modelFactory.createOpenElementTag("div", modalAttrs, null, false));
		createHeader(modalModel, modelFactory, modalAttribute);
		if (bodyModel != null) {
			modalModel.addModel(bodyModel);
		}
		if (footerModel != null) {
			modalModel.addModel(footerModel);
		}
		modalModel.add(modelFactory.createCloseElementTag("div"));
		return modalModel;
	}

	private void createHeader(IModel modalModel, IModelFactory modelFactory, ModalAttribute modalAttribute) {
		if (modalAttribute.title != null) {
			Map<String, String> modalHeaderAttrs = new HashMap<>();
			modalHeaderAttrs.put("type", "button");
			modalHeaderAttrs.put("class", "close");
			modalHeaderAttrs.put("data-dismiss", "modal");
			modalModel.add(modelFactory.createOpenElementTag("div", "class", "modal-header"));
			modalModel.add(modelFactory.createOpenElementTag("button", modalHeaderAttrs, null, false));
			modalModel.add(modelFactory.createCloseElementTag("button"));
			modalModel.add(modelFactory.createOpenElementTag("h4", "class", "modal-title"));
			modalModel.add(modelFactory.createText(modalAttribute.title));
			modalModel.add(modelFactory.createCloseElementTag("h4"));
			modalModel.add(modelFactory.createCloseElementTag("div"));
		}
	}

	private IModel createBody(IModelFactory modelFactory, ModalAttribute modalAttribute) {
		IModel bodyModel = modelFactory.createModel();
		bodyModel.add(modelFactory.createOpenElementTag("div", "class", "modal-body"));
		bodyModel.add(modelFactory.createText(modalAttribute.content.toString()));
		bodyModel.add(modelFactory.createCloseElementTag("div"));
		return bodyModel;
	}

	private IModel createFooter(IModelFactory modelFactory, ModalAttribute modalAttribute) {
		IModel footerModel = modelFactory.createModel();
		footerModel.add(modelFactory.createOpenElementTag("div", "class", "modal-footer"));
		if (modalAttribute.footerCloseButton != null && "true".equals(modalAttribute.footerCloseButton)) {
			Map<String, String> modalHeaderAttrs = new HashMap<>();
			modalHeaderAttrs.put("type", "button");
			modalHeaderAttrs.put("class", "btn btn-outline dark");
			modalHeaderAttrs.put("data-dismiss", "modal");
			footerModel.add(modelFactory.createOpenElementTag("button", modalHeaderAttrs, null, false));
			footerModel.add(modelFactory.createText("关闭"));
			footerModel.add(modelFactory.createCloseElementTag("button"));
		}
		footerModel.add(modelFactory.createText(modalAttribute.content.toString()));
		footerModel.add(modelFactory.createCloseElementTag("div"));
		return footerModel;
	}

	private class ModalAttribute {
		public String id;// modal的id属性
		public String title;// modal的标题
		public String width;// 宽度
		public String height;// 高度
		public String animation;// 动画，默认fade
		public String footerCloseButton;// footer中是否需要close按钮

		public StringBuilder content = new StringBuilder();// 非属性
		public boolean isStart;// 非属性
	}
}
