package com.expect.admin.web.controller.custom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.DataTableLocal;
import com.expect.admin.service.impl.custom.AuthorityService;
import com.expect.admin.service.impl.custom.RoleService;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.AuthorityVo;
import com.expect.admin.service.vo.custom.RoleVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 权限管理Controller
 */
@Controller
@RequestMapping("/admin/authority")
public class AuthorityController {

	private final String viewName = "admin/system/custom/authority/";

	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 权限-管理页面
	 */
	@PageEnter
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = authorityService.getAuthorityDtrsv();
		DataTableLocal.set(dtrvs);
		return new ModelAndView(viewName + "manage");
	}

	/**
	 * 权限-配置页面
	 */
	@PageEnter
	@RequestMapping(value = "/configurationPage", method = RequestMethod.GET)
	public ModelAndView configurationPage() {
		List<RoleVo> roles = roleService.getRoles();
		ModelAndView modelAndView = new ModelAndView(viewName + "configuration");
		modelAndView.addObject("roles", roles);
		return modelAndView;
	}
	
	/**
	 * 角色-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView departmentFormPage(String id) {
		AuthorityVo authority = authorityService.getAuthorityById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/form");
		modelAndView.addObject("authority", authority);
		return modelAndView;
	}

	/**
	 * 角色-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(AuthorityVo authority) throws AjaxRequestException {
		return authorityService.save(authority);
	}

	/**
	 * 角色-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(AuthorityVo authority) throws AjaxRequestException {
		return authorityService.update(authority);
	}

	/**
	 * 角色-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return authorityService.delete(id);
	}

	/**
	 * 角色-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return authorityService.deleteBatch(ids);
	}

	/**
	 * 权限-获取权限的cbsv模型
	 */
	@RequestMapping(value = "/getAuthorityCheckboxHtml", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public CheckboxsVo getAuthorityCheckboxHtml(String roleId, String functionId) throws AjaxRequestException {
		return authorityService.getAuthorityCbsvByRoleAndFunctionId(roleId, functionId);
	}

	/**
	 * 权限-配置权限
	 */
	@RequestMapping(value = "/configureAuthority", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo configureAuthority(String roleId, String functionId, String authorityIds)
			throws AjaxRequestException {
		return authorityService.configureAuthority(roleId, functionId, authorityIds);
	}
}
