package com.expect.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.Constants;
import com.expect.admin.web.interceptor.handler.InterceptorOperation;
import com.expect.admin.web.interceptor.handler.impl.AuthInterceptorOperation;
import com.expect.admin.web.interceptor.handler.impl.SessionInterceptorOperation;

/**
 * 全局拦截器
 * 
 * 作用： <br>
 * 1.判断session是否过期； <br>
 * 2.权限验证； <br>
 * 3.保存function实体 <br>
 * 
 * 1.判断session：如果session中不存在用户，则登录超时，进行超时处理 <br>
 * 2.权限验证：主要用于判断用户是否有页面权限或者页面中某些操作的权限 <br>
 * 3.保存function实体：主要用于解析页面标签<ex:pageHeader/>和<ex:portlet-title/> <br>
 * 
 * 注意：<br>
 * 1.加入PageSign注解，才会进行权限验证和保存function实体。 <br>
 * 2.请求URL中如果没有functionId则视为没有权限
 * 
 */
public class AdminGlobalInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 排除登录
		String url = request.getRequestURI();
		if (url.contains(Constants.LOGIN_URL)) {
			response.addHeader("Location", url);
			return true;
		}
		boolean returnResult = true;
		// 超时处理
		InterceptorOperation sessionInterceptorOperation = new SessionInterceptorOperation();
		returnResult = sessionInterceptorOperation.operation(request, response, handler);
		if (!returnResult) {
			return false;
		}

		// 处理权限
		InterceptorOperation authInterceptorOperation = new AuthInterceptorOperation();
		returnResult = authInterceptorOperation.operation(request, response, handler);
		if (!returnResult) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
