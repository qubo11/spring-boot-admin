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

/**
 * 页面头部标签：<ex:pageHeader/>
 */
public class PageHeaderElementTagProcessor extends AbstractElementTagProcessor {

	public PageHeaderElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, "pageHeader", true, null, false, 200000);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		User user = User.getUser();
		if (user != null) {
			Function function = user.getCurrentFunction();
			if (function != null) {
				final IModelFactory modelFactory = context.getModelFactory();
				final IModel model = modelFactory.createModel();
				// 创建页面的头部
				createHeaderModel(modelFactory, model, function);
				// 创建头部下的导航
				createNavModel(modelFactory, model, function);

				// 把新模型替换标签
				structureHandler.replaceWith(model, false);
			}
		}
	}

	// 创建页面的头部
	private void createHeaderModel(IModelFactory modelFactory, IModel model, Function function) {
		model.add(modelFactory.createOpenElementTag("div", "class", "page-head"));
		model.add(modelFactory.createOpenElementTag("div", "class", "page-title"));
		// 创建title
		model.add(modelFactory.createOpenElementTag("h1"));
		if (!StringUtils.isEmpty(function.getName())) {
			model.add(modelFactory.createText(function.getName()));
		}
		model.add(modelFactory.createOpenElementTag("small"));
		if (!StringUtils.isEmpty(function.getDescription())) {
			model.add(modelFactory.createText(function.getDescription()));
		}
		model.add(modelFactory.createCloseElementTag("small"));
		model.add(modelFactory.createCloseElementTag("h1"));
		model.add(modelFactory.createCloseElementTag("div"));
		model.add(modelFactory.createCloseElementTag("div"));
	}

	// 创建头部下的导航
	private void createNavModel(IModelFactory modelFactory, IModel model, Function function) {
		model.add(modelFactory.createOpenElementTag("ul", "class", "page-breadcrumb breadcrumb"));
		// 把所有父导航显示出来
		Function parentFunction = function.getParentFunction();
		if (parentFunction != null) {
			Function parentParentFunction = parentFunction.getParentFunction();
			if (parentParentFunction != null) {
				addNavModel(modelFactory, model, parentParentFunction);
			}
			addNavModel(modelFactory, model, parentFunction);
		}
		// 创建当前的导航
		model.add(modelFactory.createOpenElementTag("li"));
		model.add(modelFactory.createOpenElementTag("span", "class", "active"));
		if (!StringUtils.isEmpty(function.getName())) {
			model.add(modelFactory.createText(function.getName()));
		}
		model.add(modelFactory.createCloseElementTag("span"));
		model.add(modelFactory.createCloseElementTag("li"));
		model.add(modelFactory.createCloseElementTag("ul"));
	}

	// 增加父导航
	private void addNavModel(IModelFactory modelFactory, IModel model, Function function) {
		model.add(modelFactory.createOpenElementTag("li"));
		model.add(modelFactory.createOpenElementTag("a"));
		if (!StringUtils.isEmpty(function.getName())) {
			model.add(modelFactory.createText(function.getName()));
		}
		model.add(modelFactory.createCloseElementTag("a"));
		model.add(modelFactory.createOpenElementTag("i", "class", "fa fa-circle"));
		model.add(modelFactory.createCloseElementTag("i"));
		model.add(modelFactory.createCloseElementTag("li"));
	}
}
