package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.PojoConstants;
import com.expect.admin.service.impl.db.DaoMethodService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.DaoMethodVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

@Controller
@RequestMapping(value = "/admin/db/daoMethod")
public class DaoMethodController {

	private final String viewName = "admin/system/db/dao/method/";

	@Autowired
	private DaoMethodService daoMethodService;

	/**
	 * DaoMethod-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String daoId, String functionId) {
		List<DataTableRowVo> dtrvs = daoMethodService.getDaoMethodDtrvs(daoId);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("daoMethods", dtrvs);
		modelAndView.addObject("daoId", daoId);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * DaoMethod-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id, String daoId) {
		DaoMethodVo daoMethod = daoMethodService.getDaoMethodById(id, daoId);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("daoMethod", daoMethod);
		modelAndView.addObject("daoId", daoId);
		modelAndView.addObject("baseTypes", PojoConstants.getPropertyTypes());
		return modelAndView;
	}

	/**
	 * DaoMethod-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		DaoMethodVo daoMethod = daoMethodService.getDaoMethodById(id, null);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("daoMethod", daoMethod);
		return modelAndView;
	}

	/**
	 * DaoMethod-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String daoId) {
		List<DataTableRowVo> dtrvs = daoMethodService.getDaoMethodDtrvs(daoId);
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		modelAndView.addObject("daoMethods", dtrvs);
		modelAndView.addObject("daoId", daoId);
		return modelAndView;
	}

	/**
	 * DaoMethod-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(DaoMethodVo daoMethodVo) throws AjaxRequestException {
		return daoMethodService.save(daoMethodVo);
	}

	/**
	 * DaoMethod-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(DaoMethodVo daoMethodVo) throws AjaxRequestException {
		return daoMethodService.update(daoMethodVo);
	}

	/**
	 * DaoMethod-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return daoMethodService.delete(id);
	}

	/**
	 * DaoMethod-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return daoMethodService.deleteBatch(ids);
	}

	/**
	 * DaoMethod-批量删除
	 */
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	@ResponseBody
	// @AjaxException
	public ResultVo generate(String methodName, String operations, String properties, String addProperties,
			String addPropertyTypes) {
		return daoMethodService.generate(methodName, operations, properties, addProperties, addPropertyTypes);
	}

}
