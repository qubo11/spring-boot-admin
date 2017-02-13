package com.expect.admin.web.dialect.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * portlet 标签
 * 
 * ex:portlet
 */
public class PortletElementProcessor extends AbstractElementTagProcessor {

	public PortletElementProcessor(TemplateMode templateMode, String dialectPrefix, String elementName,
			boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence) {
		super(TemplateMode.HTML, dialectPrefix, "portlet", true, attributeName, prefixAttributeName, 1000);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
	}

}
