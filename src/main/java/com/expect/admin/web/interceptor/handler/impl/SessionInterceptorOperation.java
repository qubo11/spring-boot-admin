package com.expect.admin.web.interceptor.handler.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expect.admin.contants.Constants;
import com.expect.admin.web.interceptor.handler.InterceptorOperation;
import com.expect.custom.service.vo.component.ResultVo;
import com.expect.custom.utils.RequestUtil;
import com.expect.custom.utils.ResponseBuilder;

/**
 * session过期处理:<br>
 * 如果session中不存在用户，则登录超时，进行超时处理 <br>
 */
public class SessionInterceptorOperation implements InterceptorOperation {

	@Override
	public boolean operation(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String timeout = (String) request.getSession().getAttribute("timeout");
		if (timeout == null) {
			try {
				handlerTimeoutResult(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	/**
	 * 处理session过期返回情况
	 */
	private void handlerTimeoutResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isAjax = RequestUtil.isAjax(request);
		System.out.println("isAjax:" + isAjax);
		if (isAjax) {
			ResultVo rv = new ResultVo();
			rv.setMessage("登录超时，请重新登录");
			rv.setResult(false);
			String loginUrl = Constants.LOGIN_URL;
			loginUrl = loginUrl.substring(1, loginUrl.length());
			rv.setObj(loginUrl);
			rv.setCode(Constants.CODE_TIMEOUT);
			ResponseBuilder.writeJsonResponse(response, rv);
		} else {
			response.sendRedirect(request.getContextPath() + Constants.LOGIN_URL);
		}
	}
}
