package com.expect.admin.web.controller;

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
import com.expect.admin.data.dataobject.User;
import com.expect.admin.service.convertor.UserConvertor;
import com.expect.admin.service.impl.AttachmentService;
import com.expect.admin.service.impl.UserService;
import com.expect.admin.service.vo.AttachmentVo;
import com.expect.admin.service.vo.UserVo;
import com.expect.admin.service.vo.component.FileResultVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.utils.IOUtil;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

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
	@RequestMapping(value = "/userFormPage", method = RequestMethod.GET)
	public ModelAndView userForm(String id) {
		UserVo user = userService.getUserById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/userForm");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	/**
	 * 用户表单页面
	 */
	@RequestMapping(value = "/userDetailPage", method = RequestMethod.GET)
	public ModelAndView userDetailPage(String id) {
		UserVo user = userService.getUserById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail/userDetail");
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
	@AjaxException
	public DataTableRowVo save(UserVo userVo) throws AjaxRequestException {
		return userService.save(userVo);
	}

	/**
	 * 用户-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(UserVo userVo) throws AjaxRequestException {
		return userService.update(userVo);
	}

	/**
	 * 用户-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return userService.delete(id);
	}

	/**
	 * 用户-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return userService.deleteBatch(ids);
	}

	/**
	 * 修改用户角色
	 */
	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo updateUserRole(String userId, String roleId) throws AjaxRequestException {
		ResultVo resultVo = userService.updateUserRole(userId, roleId);
		return resultVo;
	}

	/**
	 * 修改用户部门
	 */
	@RequestMapping(value = "/updateUserDepartment", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo updateUserDepartment(String userId, String departmentId) throws AjaxRequestException {
		ResultVo resultVo = userService.updateUserDepartment(userId, departmentId);
		return resultVo;
	}

	/**
	 * 检查头像
	 */
	@RequestMapping(value = "/checkAvatar", method = RequestMethod.GET)
	@ResponseBody
	@AjaxException
	public ResultVo checkAvatar(String userId) throws AjaxRequestException {
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
	}

	/**
	 * 显示头像
	 */
	@RequestMapping(value = "/showAvatar", method = RequestMethod.GET)
	@AjaxException
	public void showAvatar(String userId, HttpServletResponse response) throws AjaxRequestException {
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
	}

	/**
	 * 修改头像
	 */
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo uploadAvatar(MultipartFile files, String userAvatarId, HttpServletRequest request)
			throws AjaxRequestException {
		String avatarPath = settings.getAvatarPath();
		FileResultVo frv = attachmentService.save(files, avatarPath);
		if (!frv.isResult()) {
			return frv;
		}
		ResultVo rv = userService.updateAvatar(userAvatarId, frv.getId());
		return rv;
	}

	/**
	 * 用户管理页面
	 */
	@RequestMapping(value = "/individualPage", method = RequestMethod.GET)
	public ModelAndView individualPage() {
		User user = User.getUser();
		ModelAndView modelAndView = new ModelAndView(viewName + "individual/individual");
		UserVo userVo = userService.getUserById(user.getId());
		modelAndView.addObject("user", userVo);
		return modelAndView;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo modifyPassword(String id, String oldPassword, String newPassword, String newPasswordRepeat)
			throws AjaxRequestException {
		ResultVo rv = userService.updatePassword(id, oldPassword, newPassword, newPasswordRepeat);
		return rv;
	}
}
