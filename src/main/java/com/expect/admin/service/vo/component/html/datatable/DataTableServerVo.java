package com.expect.admin.service.vo.component.html.datatable;

import java.util.ArrayList;
import java.util.List;

import com.expect.admin.service.vo.component.BaseVo;

public class DataTableServerVo<T> extends BaseVo {

	public static String checkbox = "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'> <input type='checkbox' class='checkboxes' /><span></span></label>";

	protected int draw;
	protected long recordsTotal;
	protected long recordsFiltered;
	protected List<T> data = new ArrayList<>();

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
