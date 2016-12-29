package com.expect.admin.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.RoleService;
import com.expect.admin.service.convertor.RoleConvertor;
import com.expect.admin.service.vo.RoleVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.JsTreeVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 角色管理Controller
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {

	private final String viewName = "admin/system/role/";

	@Autowired
	private RoleService roleService;

	/**
	 * 角色-管理页面
	 */
	@RequestMapping("/roleManagePage")
	public ModelAndView userManagePage() {
		List<RoleVo> roles = roleService.getRoles();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("roles", roles);
		return modelAndView;
	}

	/**
	 * 获取对应角色的功能tree
	 */
	@RequestMapping("/getFunctionTree")
	@ResponseBody
	@AjaxException
	public List<JsTreeVo> getFunctionTree(String roleId) throws AjaxRequestException {
		return roleService.getFunctionTreeByRoleId(roleId);
	}

	/**
	 * 角色-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo save(String name) throws AjaxRequestException {
		return roleService.save(name);
	}

	/**
	 * 角色-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo update(String id, String name) throws AjaxRequestException {
		return roleService.update(id, name);
	}

	/**
	 * 角色-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return roleService.delete(id);
	}

	/**
	 * 修改角色功能
	 */
	@RequestMapping(value = "/updateRoleFunctions", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo updateRoleFunctions(String roleId, String functionIds) throws AjaxRequestException {
		return roleService.updateRoleFunctions(roleId, functionIds);
	}

	/**
	 * 获取roleCheckbox的html
	 */
	@RequestMapping(value = "/getRoleCheckboxHtml", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
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
