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
import com.expect.admin.service.convertor.custom.RoleConvertor;
import com.expect.admin.service.impl.custom.RoleService;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.JsTreeVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.RoleVo;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.web.exception.AjaxRequest;
import com.expect.custom.web.exception.AjaxRequestException;

/**
 * 角色管理Controller
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {

	private final String viewName = "admin/system/custom/role/";

	@Autowired
	private RoleService roleService;

	/**
	 * 角色-管理页面
	 */
	@PageEnter
	@RequestMapping("/managePage")
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = roleService.getRoleDtrsv();
		DataTableLocal.set(dtrvs);
		return new ModelAndView(viewName + "manage");
	}

	/**
	 * 角色-功能配置页面
	 */
	@PageEnter
	@RequestMapping("/configurationPage")
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
		RoleVo role = roleService.getRoleById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/form");
		modelAndView.addObject("role", role);
		return modelAndView;
	}

	/**
	 * 获取对应角色的功能tree
	 */
	@RequestMapping("/getFunctionTree")
	@ResponseBody
	@AjaxRequest
	public List<JsTreeVo> getFunctionTree(String roleId) throws AjaxRequestException {
		return roleService.getFunctionTreeByRoleId(roleId);
	}

	/**
	 * 获取对应角色的功能tree
	 */
	@RequestMapping("/getOwnFunctionTree")
	@ResponseBody
	@AjaxRequest
	public List<JsTreeVo> getOwnFunctionTree(String roleId) throws AjaxRequestException {
		return roleService.getOwnFunctionTreeByRoleId(roleId);
	}

	/**
	 * 角色-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo save(RoleVo role) throws AjaxRequestException {
		return roleService.save(role);
	}

	/**
	 * 角色-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public DataTableRowVo update(RoleVo role) throws AjaxRequestException {
		return roleService.update(role);
	}

	/**
	 * 角色-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo delete(String id) throws AjaxRequestException {
		return roleService.delete(id);
	}

	/**
	 * 角色-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return roleService.deleteBatch(ids);
	}

	/**
	 * 修改角色功能
	 */
	@RequestMapping(value = "/updateRoleFunctions", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public ResultVo updateRoleFunctions(String roleId, String functionIds) throws AjaxRequestException {
		return roleService.updateRoleFunctions(roleId, functionIds);
	}

	/**
	 * 获取roleCheckbox的html
	 */
	@RequestMapping(value = "/getRoleCheckboxHtml", method = RequestMethod.POST)
	@ResponseBody
	@AjaxRequest
	public CheckboxsVo getRoleCheckboxHtml(String userId) throws AjaxRequestException {
		List<RoleVo> roles = roleService.getRoles();
		List<RoleVo> userRoles = roleService.getRolesByUserId(userId);
		List<String> ids = new ArrayList<>();
		if (!CollectionUtils.isEmpty(userRoles)) {
			for (RoleVo role : userRoles) {
				ids.add(role.getId());
			}
		}
		CheckboxsVo cbv = RoleConvertor.convertCbv(roles, ids);
		return cbv;
	}

}
