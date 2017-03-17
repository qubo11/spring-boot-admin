package com.expect.admin.web.controller.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.DataTableLocal;
import com.expect.admin.service.impl.custom.CompanyService;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.CompanyVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 公司Controller
 */
public class CompanyController {
	
	private final String viewName = "admin/system/custom/company/";

	@Autowired
	private CompanyService companyService;

	/**
	 * 公司-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = companyService.getCompanyDtrsv();
		DataTableLocal.set(dtrvs);
		return new ModelAndView(viewName + "manage");
	}

	/**
	 * 公司-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView departmentFormPage(String id) {
		CompanyVo company = companyService.getCompanyById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/form");
		modelAndView.addObject("company", company);
		return modelAndView;
	}

	/**
	 * 公司-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(CompanyVo company) throws AjaxRequestException {
		return companyService.save(company);
	}

	/**
	 * 公司-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(CompanyVo company) throws AjaxRequestException {
		return companyService.update(company);
	}

	/**
	 * 公司-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return companyService.delete(id);
	}

	/**
	 * 公司-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return companyService.deleteBatch(ids);
	}

}
