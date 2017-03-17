package com.expect.admin.web.dialect.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import com.expect.admin.data.dataobject.custom.Function;
import com.expect.admin.data.dataobject.custom.User;
import com.expect.admin.service.vo.component.html.page.portlet.ActionsVo;
import com.expect.admin.service.vo.component.html.page.portlet.CaptionVo;
import com.expect.admin.service.vo.component.html.page.portlet.ToolsVo;

/**
 * portlet头部标签：<ex:portlet-title />
 */
public class PortletTitleElementTagProcessor extends AbstractElementTagProcessor {

	public PortletTitleElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, "portlet-title", true, null, false, 20000);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		PortletTitleAttribute portletTitleAttribute = new PortletTitleAttribute();

		String title = tag.getAttributeValue("title");
		String subTitle = tag.getAttributeValue("subTitle");
		String icon = tag.getAttributeValue("icon");
		String type = tag.getAttributeValue("type");
		String actionsName = tag.getAttributeValue("actionsName");
		String actionsClass = tag.getAttributeValue("actionsClass");
		String actionsIcon = tag.getAttributeValue("actionsIcon");
		portletTitleAttribute.title = title;
		portletTitleAttribute.subTitle = subTitle;
		portletTitleAttribute.icon = icon;
		portletTitleAttribute.type = type;
		portletTitleAttribute.actionsName = actionsName;
		portletTitleAttribute.actionsClass = actionsClass;
		portletTitleAttribute.actionsIcon = actionsIcon;

		User user = User.getUser();
		if (user != null) {
			Function function = user.getCurrentFunction();
			if (function != null) {
				if (StringUtils.isBlank(portletTitleAttribute.title)) {
					portletTitleAttribute.title = function.getName();
				}
				if (StringUtils.isBlank(portletTitleAttribute.icon)) {
					portletTitleAttribute.icon = function.getIcon();
				}
			}
		}

		final IModelFactory modelFactory = context.getModelFactory();
		final IModel model = modelFactory.createModel();

		// 创建portlet-title
		createTitleModel(modelFactory, model, portletTitleAttribute);

		// 把新模型替换标签
		structureHandler.replaceWith(model, false);
	}

	// 创建portlet-title
	private void createTitleModel(IModelFactory modelFactory, IModel model,
			PortletTitleAttribute portletTitleAttribute) {
		model.add(modelFactory.createOpenElementTag("div", "class", "portlet-title"));

		// 创建caption
		createCaptionModel(modelFactory, model, portletTitleAttribute);
		// 创建actions
		createActionsModel(modelFactory, model, portletTitleAttribute);
		// 创建tools
		createToolsModel(modelFactory, model, portletTitleAttribute);

		model.add(modelFactory.createCloseElementTag("div"));
	}

	// 创建caption
	private void createCaptionModel(IModelFactory modelFactory, IModel model,
			PortletTitleAttribute portletTitleAttribute) {
		CaptionVo caption = new CaptionVo();
		caption.setTitle(portletTitleAttribute.title);
		caption.setSubTitle(portletTitleAttribute.subTitle);
		caption.setIcon(portletTitleAttribute.icon);
		model.add(modelFactory.createText(caption.getHtml(portletTitleAttribute.type)));
	}

	// 创建actions
	private void createActionsModel(IModelFactory modelFactory, IModel model,
			PortletTitleAttribute portletTitleAttribute) {
		ActionsVo actionsVo = new ActionsVo();
		actionsVo.setDefaultAction();
		String actionsName = portletTitleAttribute.actionsName;
		String actionsClass = portletTitleAttribute.actionsClass;
		String actionsIcon = portletTitleAttribute.actionsIcon;
		if (!StringUtils.isBlank(actionsName)) {
			String[] actionsNameArr = actionsName.split(",");
			String[] actionsClassArr = null;
			if (!StringUtils.isBlank(actionsClass)) {
				actionsClassArr = actionsClass.split(",");
			}
			String[] actionsIconArr = null;
			if (!StringUtils.isBlank(actionsIcon)) {
				actionsIconArr = actionsIcon.split(",");
			}
			for (int i = 0; i < actionsNameArr.length; i++) {
				String className = null;
				if (actionsClassArr != null && actionsClassArr.length > i) {
					className = actionsClassArr[i];
				}
				String icon = null;
				if (actionsIconArr != null && actionsIconArr.length > i) {
					icon = actionsIconArr[i];
				}
				String button = actionsVo.createAction(actionsNameArr[i], className, icon);
				actionsVo.add(button);
			}
		}
		model.add(modelFactory.createText(actionsVo.getHtml()));
	}

	private void createToolsModel(IModelFactory modelFactory, IModel model,
			PortletTitleAttribute portletTitleAttribute) {
		ToolsVo toolsVo = new ToolsVo();
		String tools = portletTitleAttribute.tools;
		if (!StringUtils.isBlank(tools)) {
			if (tools.contains("c")) {
				toolsVo.setCollapse();
			}
			if (tools.contains("f")) {
				toolsVo.setFullscreen();
			}
			if (tools.contains("r")) {
				toolsVo.setRemove();
			}
		}
		model.add(modelFactory.createText(toolsVo.getHtml()));
	}

	private class PortletTitleAttribute {
		public String title;// 标题
		public String subTitle;// 子标题
		public String icon;// 图标
		public String type;// portlet类型(b,l,s)，b代表boxed/l代表light/s代表solid
		public String tools;// 工具按钮(c,f,r)，只要包含这三个就会增加相应的按钮
		public String actionsName;// action名称(使用,分隔)
		public String actionsClass;// action类名(使用,分隔)
		public String actionsIcon;// action图标(使用,分隔)
	}
}
