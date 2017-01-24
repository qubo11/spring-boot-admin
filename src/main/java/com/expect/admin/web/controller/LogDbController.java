package com.expect.admin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.data.dataobject.log.LogDb;
import com.expect.admin.service.convertor.LogDbConvertor;
import com.expect.admin.service.impl.LogDbService;
import com.expect.admin.service.vo.LogDbVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableServerArrayVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 日志记录Controller
 */
@Controller
@RequestMapping("/admin/logDb")
public class LogDbController {

	private final String viewName = "admin/system/logDb/";

	@Autowired
	private LogDbService logDbService;

	/**
	 * 日志记录-管理页面
	 */
	@RequestMapping(value = "/logDbManagePage", method = RequestMethod.GET)
	public ModelAndView logDbManage() {
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		return modelAndView;
	}

	/**
	 * 日志记录-详情页面
	 */
	@RequestMapping(value = "/logDbDetailPage", method = RequestMethod.POST)
	public ModelAndView logDbDetail(String id) {
		ModelAndView modelAndView = new ModelAndView(viewName + "detail/logDbDetail");
		LogDbVo logDbVo = logDbService.getLogDbById(id);
		modelAndView.addObject("logDb", logDbVo);
		return modelAndView;
	}

	/**
	 * 日志记录-获取日志记录Datatable
	 */
	@RequestMapping(value = "/getLogDbDatatable", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableServerArrayVo getLogDbDatatable(LogDbVo logDbVo, String start, String length)
			throws AjaxRequestException {
		Page<LogDb> logDbPage = logDbService.getLogs(logDbVo, start, length);
		List<LogDb> logDbs = logDbPage.getContent();
		DataTableServerArrayVo dtsrv = LogDbConvertor.convertDtsrv(logDbs);
		dtsrv.setRecordsFiltered(logDbPage.getTotalElements());
		dtsrv.setRecordsTotal(logDbPage.getTotalElements());
		return dtsrv;
	}

}
