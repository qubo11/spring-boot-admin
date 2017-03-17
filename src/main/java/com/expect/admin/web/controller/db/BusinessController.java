package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.BusinessService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.BusinessVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 业务 Controller
 */
@Controller
@RequestMapping(value = "/admin/db/business")
public class BusinessController {

	private final String viewName = "admin/system/db/business/";

	@Autowired
	private BusinessService businessService;

	/**
	 * 业务-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage(String functionId) {
		List<DataTableRowVo> dtrvs = businessService.getBusinessDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("businesss", dtrvs);
		modelAndView.addObject("functionId", functionId);
		return modelAndView;
	}

	/**
	 * 业务-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		BusinessVo business = businessService.getBusinessById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("business", business);
		return modelAndView;
	}

	/**
	 * 业务-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		BusinessVo business = businessService.getBusinessById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("business", business);
		return modelAndView;
	}

	/**
	 * 业务-配置向导页面
	 */
	@RequestMapping(value = "/guidePage", method = RequestMethod.GET)
	public ModelAndView guidePage(String pojoId) {
		ModelAndView modelAndView = new ModelAndView(viewName + "guide");
		BusinessVo business = businessService.getBusinessByPojoId(pojoId);
		modelAndView.addObject("business", business);
		modelAndView.addObject("pojoId", pojoId);
		return modelAndView;
	}

	/**
	 * 业务-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(BusinessVo businessVo) throws AjaxRequestException {
		return businessService.save(businessVo);
	}

	/**
	 * 业务-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(BusinessVo businessVo) throws AjaxRequestException {
		return businessService.update(businessVo);
	}

	/**
	 * 业务-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return businessService.delete(id);
	}

	/**
	 * 业务-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return businessService.deleteBatch(ids);
	}

	/**
	 * 业务-保存或者更新
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo saveOrUpdate(BusinessVo businessVo) throws AjaxRequestException {
		return businessService.saveOrUpdate(businessVo);
	}

}
