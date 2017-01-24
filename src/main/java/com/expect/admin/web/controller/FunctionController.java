package com.expect.admin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.convertor.FunctionConvertor;
import com.expect.admin.service.impl.FunctionService;
import com.expect.admin.service.vo.FunctionVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 功能Controller
 */
@Controller
@RequestMapping("/admin/function")
public class FunctionController {

	private final String viewName = "admin/system/function/";

	@Autowired
	private FunctionService functionService;

	/**
	 * 功能-管理页面
	 */
	@RequestMapping(value = "/functionManagePage", method = RequestMethod.GET)
	public ModelAndView userManagePage() {
		List<FunctionVo> functions = functionService.getFunctions();
		List<DataTableRowVo> dtrvs = FunctionConvertor.convertDtrv(functions);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("functions", dtrvs);
		return modelAndView;
	}

	/**
	 * 功能-表单页面
	 */
	@RequestMapping(value = "/functionFormPage", method = RequestMethod.GET)
	public ModelAndView functionForm(String id) {
		FunctionVo function = functionService.getFunctionById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/functionForm");
		modelAndView.addObject("function", function);
		return modelAndView;
	}

	/**
	 * 功能-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(FunctionVo functionVo) throws AjaxRequestException{
		return functionService.save(functionVo);
	}

	/**
	 * 功能-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(FunctionVo functionVo) throws AjaxRequestException{
		return functionService.update(functionVo);
	}

	/**
	 * 功能-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException{
		return functionService.delete(id);
	}

	/**
	 * 功能-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException{
		return functionService.deleteBatch(ids);
	}
}
