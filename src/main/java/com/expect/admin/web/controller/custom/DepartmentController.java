package com.expect.admin.web.controller.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.DataTableLocal;
import com.expect.admin.service.convertor.custom.DepartmentConvertor;
import com.expect.admin.service.impl.custom.DepartmentService;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.DepartmentVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 部门管理Controller
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentController {

	private final String viewName = "admin/system/custom/department/";

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 部门-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = departmentService.getDepartmentDtrsv();
		DataTableLocal.set(dtrvs);
		return new ModelAndView(viewName + "manage");
	}

	/**
	 * 部门-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView departmentFormPage(String id) {
		DepartmentVo department = departmentService.getDepartmentById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/departmentForm");
		modelAndView.addObject("department", department);
		return modelAndView;
	}

	/**
	 * 部门-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(DepartmentVo departmentVo) throws AjaxRequestException {
		return departmentService.save(departmentVo);
	}

	/**
	 * 部门-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(DepartmentVo departmentVo) throws AjaxRequestException {
		return departmentService.update(departmentVo);
	}

	/**
	 * 部门-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return departmentService.delete(id);
	}

	/**
	 * 部门-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return departmentService.deleteBatch(ids);
	}

	/**
	 * 获取departmentCheckbox的html
	 */
	@RequestMapping(value = "/getDepartmentCheckboxHtml", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public CheckboxsVo getDepartmentCheckboxHtml(String userId) throws AjaxRequestException {
		List<DepartmentVo> departments = departmentService.getAllBottomDepartments();
		List<String> ids = new ArrayList<>();
		List<DepartmentVo> userDepartments = departmentService.getDepartmentsByUserId(userId);
		if (!CollectionUtils.isEmpty(userDepartments)) {
			for (DepartmentVo department : userDepartments) {
				ids.add(department.getId());
			}
		}
		CheckboxsVo cbv = DepartmentConvertor.voToCbv(departments, ids);
		return cbv;
	}

}
