package com.expect.admin.contants;

import java.util.List;

import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;

public class DataTableLocal {

	private static ThreadLocal<List<DataTableRowVo>> dtrvsLocal = new ThreadLocal<>();

	public static List<DataTableRowVo> get() {
		return dtrvsLocal.get();
	}

	public static void set(List<DataTableRowVo> dtrvs) {
		dtrvsLocal.set(dtrvs);
	}

	public static void remove() {
		dtrvsLocal.remove();
	}
}
