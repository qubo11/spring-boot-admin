package com.expect.admin.web.interceptor.handler.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;

import com.expect.admin.contants.Constants;
import com.expect.admin.data.dao.custom.FunctionRepository;
import com.expect.admin.data.dao.custom.RoleFunctionRepository;
import com.expect.admin.data.dataobject.custom.Authority;
import com.expect.admin.data.dataobject.custom.Function;
import com.expect.admin.data.dataobject.custom.Role;
import com.expect.admin.data.dataobject.custom.RoleFunction;
import com.expect.admin.data.dataobject.custom.User;
import com.expect.admin.web.interceptor.PageEnter;
import com.expect.admin.web.interceptor.handler.InterceptorOperation;
import com.expect.custom.utils.SpringUtil;

/**
 * 权限处理: <br>
 * 1.权限验证：主要用于判断用户是否有页面权限或者页面中某些操作的权限 <br>
 * 2.保存function实体：主要用于解析页面标签<ex:pageHeader/>和<ex:portlet-title/> <br>
 * 
 * 注意：<br>
 * 1.加入PageSign注解，才会进行权限验证和保存function实体。 <br>
 * 2.请求URL中如果没有functionId则视为没有权限
 */
public class AuthInterceptorOperation implements InterceptorOperation {

	@Override
	public boolean operation(HttpServletRequest request, HttpServletResponse response, Object handler) {
		User user = User.getUser();
		if (user == null) {
			handlerNoAuthResult(request, response);
			return false;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		PageEnter pageEnter = method.getAnnotation(PageEnter.class);
		// 进行权限验证
		if (pageEnter != null) {
			// 获取functionId
			String functionId = request.getParameter("functionId");
			// 如果functionId为空，则视为没有权限
			if (StringUtils.isBlank(functionId)) {
				handlerNoAuthResult(request, response);
				return false;
			} else {
				final ApplicationContext appCtx = SpringUtil.getApplicationContext();
				
				// 设置function
				FunctionRepository functionRepository = appCtx.getBean(FunctionRepository.class);
				Function function = functionRepository.findOne(functionId);
				user.setCurrentFunction(function);

				// 设置权限
				Set<Role> roles = user.getRoles();
				List<String> roleIds = new ArrayList<>();
				for (Role role : roles) {
					roleIds.add(role.getId());
				}
				final RoleFunctionRepository roleFunctionRepository = appCtx.getBean(RoleFunctionRepository.class);
				Set<RoleFunction> roleFunctions = roleFunctionRepository.findByRoleIdInAndFunctionId(roleIds,
						functionId);
				List<Authority> authorityResults = new ArrayList<>();
				for (RoleFunction roleFunction : roleFunctions) {
					List<Authority> authoritySet = roleFunction.getAuthorities();
					for (Authority authority : authoritySet) {
						boolean hasAuthority = false;
						for (Authority authorityResult : authorityResults) {
							if (authorityResult.getId().equals(authority.getId())) {
								hasAuthority = true;
							}
						}
						if (!hasAuthority) {
							authorityResults.add(authority);
						}
					}
				}
				Collections.sort(authorityResults);
				user.setUserAuthorities(authorityResults);

				// 判断用户是否有查看的权限，如果没有就直接返回
				boolean hasBase = false;
				for (Authority authority : authorityResults) {
					if (authority.getCode().equalsIgnoreCase(Authority.AUTHORITY_BASE)) {
						hasBase = true;
					}
				}
				if (!hasBase) {
					handlerNoAuthResult(request, response);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 处理没有权限返回
	 */
	private void handlerNoAuthResult(HttpServletRequest request, HttpServletResponse response) {
		// boolean isAjax = RequestUtil.isAjax(request);
		// if (isAjax) {
		// ResultVo rv = new ResultVo();
		// rv.setMessage("您没有该操作权限");
		// ResponseBuilder.writeJsonResponse(response, rv);
		// } else {
		try {
			response.sendRedirect(request.getContextPath() + Constants.NOAUTH_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
	}

}
