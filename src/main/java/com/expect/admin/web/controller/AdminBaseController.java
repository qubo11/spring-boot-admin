package com.expect.admin.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.expect.admin.contants.Constants;
import com.expect.admin.service.impl.custom.FunctionService;
import com.expect.admin.service.vo.custom.FunctionVo;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.utils.RequestUtil;
import com.expect.custom.utils.ResponseBuilder;

@Controller
@RequestMapping("/admin")
public class AdminBaseController {

	@Autowired
	private FunctionService functionService;

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		List<FunctionVo> functions = functionService.getFunctionsByUser();
		model.addAttribute("functions", functions);
		return "admin/home";
	}

	@RequestMapping("/home1")
	public String home1() {
		return "admin/index";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/admin/login";
	}

	@RequestMapping("/404")
	public String page404() {
		return "admin/404";
	}

	/**
	 * 没有权限
	 */
	@RequestMapping("/notAuth")
	public String pageNotAuth() {
		return "admin/notAuth";
	}

	/**
	 * 登录超时
	 */
	@RequestMapping("/sessionTimeout")
	public void pageTimeout(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean isAjax = RequestUtil.isAjax(request);
			if (isAjax) {
				ResultVo rv = new ResultVo();
				rv.setMessage("登录超时，请重新登录");
				rv.setResult(false);
				rv.setObj(Constants.LOGIN_URL);
				rv.setCode(Constants.CODE_TIMEOUT);
				ResponseBuilder.writeJsonResponse(response, rv);
			} else {
				response.sendRedirect(request.getContextPath() + Constants.LOGIN_URL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
