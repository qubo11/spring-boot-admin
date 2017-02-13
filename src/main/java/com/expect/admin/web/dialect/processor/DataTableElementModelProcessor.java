package com.expect.admin.web.dialect.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.AbstractElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.utils.Base64Util;
import com.expect.admin.utils.JacksonJsonUtil;

public class DataTableElementModelProcessor extends AbstractElementModelProcessor {

	public DataTableElementModelProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, "dt", true, null, false, 200000);
	}

	@Override
	protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		DtAttribute dtAttribute = new DtAttribute();
		final IModelFactory modelFactory = context.getModelFactory();
		IModel dtModel = null;
		int n = model.size();
		while (n-- != 0) {
			final ITemplateEvent event = model.get(n);
			if (event instanceof IProcessableElementTag) {
				IProcessableElementTag tag = ((IProcessableElementTag) event);
				String tagName = tag.getElementCompleteName();
				if ("ex:dt".equals(tagName)) {
					String id = tag.getAttributeValue("id");
					String ths = tag.getAttributeValue("ths");
					String data = tag.getAttributeValue("data");
					dtAttribute.id = id;
					dtAttribute.ths = ths;
					dtAttribute.data = data;
					dtModel = createDtModel(modelFactory, dtModel, dtAttribute);
				}
			}
			model.remove(n);
		}
		model.addModel(dtModel);
	}

	private IModel createDtModel(IModelFactory modelFactory, IModel model, DtAttribute dtAttribute) {
		IModel dtModel = modelFactory.createModel();
		Map<String, String> dtAttrs = new HashMap<>();
		dtAttrs.put("class", "table table-striped table-bordered table-hover");
		if (dtAttribute.id != null) {
			dtAttrs.put("id", dtAttribute.id);
		}
		if (dtAttribute.ths != null) {

		}
		dtModel.add(modelFactory.createOpenElementTag("table", dtAttrs, null, false));
		// 创建表头
		createDtThead(modelFactory, dtModel, dtAttribute);
		// 创建表体
		createDtTbody(modelFactory, dtModel, dtAttribute);
		dtModel.add(modelFactory.createCloseElementTag("table"));
		return dtModel;
	}

	// 创建表头
	private void createDtThead(IModelFactory modelFactory, IModel dtModel, DtAttribute dtAttribute) {
		dtModel.add(modelFactory.createOpenElementTag("thead"));
		dtModel.add(modelFactory.createOpenElementTag("tr"));
		// 解析表头
		if (dtAttribute.ths != null) {
			String[] thArr = dtAttribute.ths.split(",");
			if (thArr != null && thArr.length > 0) {
				for (String th : thArr) {
					if ("checkbox".equals(th) || "cb".equals(th)) {
						// 创建多选框
						dtModel.add(modelFactory.createOpenElementTag("th", "class", "table-checkbox mt-th-checkbox"));
						dtModel.add(modelFactory.createOpenElementTag("label", "class",
								"mt-checkbox mt-checkbox-single mt-checkbox-outline"));
						Map<String, String> inputAttrs = new HashMap<>();
						inputAttrs.put("type", "checkbox");
						inputAttrs.put("class", "group-checkable");
						inputAttrs.put("data-set", "#" + dtAttribute.id + " .checkboxes");
						dtModel.add(modelFactory.createOpenElementTag("input", inputAttrs, null, false));
						dtModel.add(modelFactory.createCloseElementTag("input"));
						dtModel.add(modelFactory.createOpenElementTag("span"));
						dtModel.add(modelFactory.createCloseElementTag("span"));
						dtModel.add(modelFactory.createCloseElementTag("label"));
					} else {
						dtModel.add(modelFactory.createOpenElementTag("th"));
						dtModel.add(modelFactory.createText(th));
					}
					dtModel.add(modelFactory.createCloseElementTag("th"));
				}
			}
		}
		dtModel.add(modelFactory.createCloseElementTag("tr"));
		dtModel.add(modelFactory.createCloseElementTag("thead"));
	}

	// 创建表体
	@SuppressWarnings("unchecked")
	private void createDtTbody(IModelFactory modelFactory, IModel dtModel, DtAttribute dtAttribute) {
		dtModel.add(modelFactory.createOpenElementTag("tbody", "class", "mt-td-checkbox"));
		String data = dtAttribute.data;
		if (data != null) {
			data = Base64Util.decode(data);
			List<DataTableRowVo> dtrvs = JacksonJsonUtil.getInstance().readList(data, DataTableRowVo.class);
			for (DataTableRowVo dtrv : dtrvs) {
				String id = "";
				if (dtrv.getObj() != null) {
					id = dtrv.getObj() + "";
				}
				System.out.println("id:" + id);
				dtModel.add(modelFactory.createOpenElementTag("tr", "id", id));
				List<String> tdData = dtrv.getData();
				for (String td : tdData) {
					System.out.println("td:" + td);
					dtModel.add(modelFactory.createOpenElementTag("td"));
					if(td!=null){
						dtModel.add(modelFactory.createText(td));
					}
					dtModel.add(modelFactory.createCloseElementTag("td"));
				}
				dtModel.add(modelFactory.createCloseElementTag("tr"));
			}
		}
		dtModel.add(modelFactory.createCloseElementTag("tbody"));
		System.out.println(dtModel);
	}

	private class DtAttribute {
		public String id;
		public String ths;// 表头(,号隔开)
		public String data;// 表格的具体数据
	}
}
