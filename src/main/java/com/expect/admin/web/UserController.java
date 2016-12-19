package com.expect.admin.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.config.Settings;
import com.expect.admin.service.AttachmentService;
import com.expect.admin.service.UserService;
import com.expect.admin.service.convertor.UserConvertor;
import com.expect.admin.service.vo.AttachmentVo;
import com.expect.admin.service.vo.UserVo;
import com.expect.admin.service.vo.component.FileResultVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.utils.IOUtil;
import com.expect.admin.utils.exception.AjaxRequestException;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	private final String viewName = "admin/system/user/";

	@Autowired
	private UserService userService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private Settings settings;

	/**
	 * 用户管理页面
	 */
	@RequestMapping(value = "/userManagePage", method = RequestMethod.GET)
	public ModelAndView userManagePage() {
		List<UserVo> users = userService.getAllUsers();
		List<DataTableRowVo> dtrvs = UserConvertor.convertDtrvs(users);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("users", dtrvs);
		return modelAndView;
	}

	/**
	 * 用户表单页面
	 */
	@RequestMapping(value = "/userFormPage", method = RequestMethod.POST)
	public ModelAndView userForm(String id) {
		UserVo user = userService.getUserById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/userForm");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	/**
	 * 获取userSelect的html
	 */
	@RequestMapping("/getUserSelectHtml")
	@ResponseBody
	public SelectOptionVo getUserSelect(String username) {
		List<UserVo> users = userService.getAllUsers();
		SelectOptionVo sov = UserConvertor.convertSov(users, username);
		return sov;
	}

	/**
	 * 用户-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public DataTableRowVo save(UserVo userVo) throws AjaxRequestException {
		try {
			return userService.save(userVo);
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 用户-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public DataTableRowVo update(UserVo userVo) throws AjaxRequestException {
		try {
			return userService.update(userVo);
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 用户-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo delete(String id) throws AjaxRequestException {
		try {
			return userService.delete(id);
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 用户-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		try {
			return userService.deleteBatch(ids);
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 修改用户角色
	 */
	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo updateUserRole(String userId, String roleId) throws AjaxRequestException {
		try {
			ResultVo resultVo = userService.updateUserRole(userId, roleId);
			return resultVo;
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 修改用户部门
	 */
	@RequestMapping(value = "/updateUserDepartment", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo updateUserDepartment(String userId, String departmentId) throws AjaxRequestException {
		try {
			ResultVo resultVo = userService.updateUserDepartment(userId, departmentId);
			return resultVo;
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 检查头像
	 */
	@RequestMapping(value = "/checkAvatar", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo checkAvatar(String userId) throws AjaxRequestException {
		try {
			UserVo user = userService.getUserById(userId);
			if (user != null && !StringUtils.isBlank(user.getAvatarId())) {
				String avatarId = user.getAvatarId();
				AttachmentVo avatar = attachmentService.getAttachmentById(avatarId);
				if (avatar != null) {
					return new ResultVo(true);
				} else {
					return new ResultVo(false);
				}
			} else {
				return new ResultVo(false);
			}
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 显示头像
	 */
	@RequestMapping(value = "/showAvatar", method = RequestMethod.GET)
	public void showAvatar(String userId, HttpServletResponse response) throws AjaxRequestException {
		try {
			UserVo user = userService.getUserById(userId);
			if (user != null && !StringUtils.isBlank(user.getAvatarId())) {
				String avatarId = user.getAvatarId();
				AttachmentVo avatar = attachmentService.getAttachmentById(avatarId);
				if (avatar != null) {
					byte[] avatarByte = IOUtil.inputDataFromFile(avatar.getPath() + File.separator + avatar.getName());
					if (avatarByte != null) {
						try {
							response.getOutputStream().write(avatarByte);
							response.getOutputStream().flush();
							response.getOutputStream().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

	/**
	 * 修改头像
	 */
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo uploadAvatar(MultipartFile files, String userAvatarId, HttpServletRequest request)
			throws AjaxRequestException {
		try {
			String avatarPath = settings.getAvatarPath();
			FileResultVo frv = attachmentService.save(files, avatarPath);
			if (!frv.isResult()) {
				return frv;
			}
			ResultVo rv = userService.updateAvatar(userAvatarId, frv.getId());
			return rv;
		} catch (Exception e) {
			throw new AjaxRequestException();
		}
	}

}
