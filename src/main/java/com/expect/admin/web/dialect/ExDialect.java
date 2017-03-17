package com.expect.admin.web.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import com.expect.admin.web.dialect.processor.DataTableElementModelProcessor;
import com.expect.admin.web.dialect.processor.ModalElementModelProcessor;
import com.expect.admin.web.dialect.processor.PageHeaderElementTagProcessor;
import com.expect.admin.web.dialect.processor.PortletTitleElementTagProcessor;

/**
 * 定义所有的标签处理器
 */
public class ExDialect extends AbstractProcessorDialect {

	private static final String DIALECT_NAME = "Expect Dialect";

	public ExDialect() {
		super(DIALECT_NAME, "ex", 200000);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new PageHeaderElementTagProcessor(dialectPrefix));
		processors.add(new DataTableElementModelProcessor(dialectPrefix));
		processors.add(new ModalElementModelProcessor(dialectPrefix));
		processors.add(new PortletTitleElementTagProcessor(dialectPrefix));

		processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
		return processors;
	}

}
