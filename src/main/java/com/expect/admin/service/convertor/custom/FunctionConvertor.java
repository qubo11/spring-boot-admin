package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Function;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.FunctionVo;

public class FunctionConvertor {

	/**
	 * do to vo
	 */
	public static FunctionVo convert(Function function, boolean isNav) {
		FunctionVo functionVo = new FunctionVo();
		if (function == null) {
			return functionVo;
		}
		BeanUtils.copyProperties(function, functionVo);
		String url = functionVo.getUrl();
		if (!StringUtils.isBlank(url)) {
			// // 如果是导航的function转换，则需要加上functionId=xxx
			if (isNav) {
				url = handleUrl(function.getId(), url);
				functionVo.setUrl(url);
			}
			url = url.replaceAll("[a-zA-Z]", "");
			url = url.replaceAll("\\D", "");
			functionVo.setEncodeUrl(url);
		}
		// 设置父功能
		if (function.getParentFunction() != null) {
			functionVo.setParentId(function.getParentFunction().getId());
			functionVo.setParentName(function.getParentFunction().getName());
		}
		return functionVo;
	}

	private static String handleUrl(String functionId, String url) {
		if (url.indexOf("?") >= 0) {
			return url + "&functionId=" + functionId;
		} else {
			return url + "?functionId=" + functionId;
		}
	}

	/**
	 * dos to vos
	 */
	public static List<FunctionVo> convert(List<Function> functions, boolean isNav) {
		List<FunctionVo> functionVos = new ArrayList<>();
		for (Function function : functions) {
			FunctionVo functionVo = convert(function, isNav);
			functionVos.add(functionVo);
		}
		return functionVos;
	}

	/**
	 * vo to do
	 */
	public static Function convert(FunctionVo functionVo) {
		Function function = new Function();
		if (NumberUtils.isDigits(functionVo.getSequenceStr())) {
			functionVo.setSequence(Integer.valueOf(functionVo.getSequenceStr()));
		}
		BeanUtils.copyProperties(functionVo, function);
		return function;
	}

	/**
	 * vo to do
	 */
	public static void convert(FunctionVo functionVo, Function function) {
		function.setName(functionVo.getName());
		function.setIcon(functionVo.getIcon());
		function.setUrl(functionVo.getUrl());
		if (NumberUtils.isDigits(functionVo.getSequenceStr())) {
			function.setSequence(Integer.valueOf(functionVo.getSequenceStr()));
		}
		function.setDescription(functionVo.getDescription());
	}

	/**
	 * vos to dtrvs
	 */
	public static List<DataTableRowVo> convertDtrv(List<Function> functions) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(functions)) {
			for (Function function : functions) {
				DataTableRowVo dtrv = new DataTableRowVo();
				convertDtrv(dtrv, function, null);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void convertDtrv(DataTableRowVo dtrv, Function function, Function parentFunction) {
		dtrv.setObj(function.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(function.getName());
		dtrv.addData(function.getIcon());
		dtrv.addData(function.getUrl());
		if (parentFunction != null) {
			dtrv.addData(parentFunction.getName());
		} else {
			dtrv.addData("");
		}
		dtrv.addData(function.getSequence() + "");
		dtrv.addData(function.getDescription());
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getDefaultButton(function.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * vos to sov
	 */
	public static SelectOptionVo convertSov(List<FunctionVo> functionVos, FunctionVo checkedFunction) {
		SelectOptionVo sov = new SelectOptionVo();
		if (!CollectionUtils.isEmpty(functionVos)) {
			sov.addOption("", "设置为上级功能");
			for (FunctionVo functionVo : functionVos) {
				if (checkedFunction != null && !StringUtils.isBlank(checkedFunction.getParentId())) {
					if (checkedFunction.getParentId().equals(functionVo.getId())) {
						sov.addOption(functionVo.getId(), functionVo.getName(), true);
					} else {
						sov.addOption(functionVo.getId(), functionVo.getName());
					}
				} else {
					sov.addOption(functionVo.getId(), functionVo.getName());
				}
			}
		}
		return sov;
	}

}
