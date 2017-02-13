package com.expect.admin.service.vo.component.html.datatable;

import java.util.List;

import com.expect.admin.utils.Base64Util;
import com.expect.admin.utils.JacksonJsonUtil;

/**
 * 存放DataTableRowVo的list数据
 */
public class DataTableRowsVo {

	public List<DataTableRowVo> dtrvs;

	public List<DataTableRowVo> getDtrvs() {
		return dtrvs;
	}

	public void setDtrvs(List<DataTableRowVo> dtrvs) {
		this.dtrvs = dtrvs;
	}

	public String getJson() {
		String json = JacksonJsonUtil.getInstance().write(dtrvs);
		if (json != null) {
			return Base64Util.encode(json);
		}
		return "";
	}
}
