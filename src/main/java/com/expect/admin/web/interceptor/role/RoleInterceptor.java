package com.expect.admin.web.interceptor.role;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.contants.RoleLocal;
import com.expect.admin.data.dao.RoleFunctionRepository;
import com.expect.admin.data.dataobject.Role;
import com.expect.admin.data.dataobject.RoleFunction;
import com.expect.admin.data.dataobject.User;
import com.expect.admin.utils.SpringUtil;

/**
 * 权限验证(增删改查)拦截器
 * 
 * 加入RoleValidate注解，才进行验证。
 * 
 * 请求URL中如果没有functionId则视为没有权限
 */
public class RoleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		RoleValidate roleValidate = method.getAnnotation(RoleValidate.class);
		// 需要进行验证
		if (roleValidate != null) {
			// 获取functionId
			String functionId = request.getParameter("functionId");
			// 如果functionId为空，则视为没有权限
			if (StringUtils.isBlank(functionId)) {
				handlerReuqest(request, response);
				return false;
			} else {
				// 如果functionId不为空，就判断是否有权限
				Boolean[] authorities = new Boolean[4];
				User user = User.getUser();
				Set<Role> roles = user.getRoles();
				List<String> roleIds = new ArrayList<>();
				for (Role role : roles) {
					roleIds.add(role.getId());
				}
				final ApplicationContext appCtx = SpringUtil.getApplicationContext();
				final RoleFunctionRepository roleFunctionRepository = appCtx.getBean(RoleFunctionRepository.class);
				Set<RoleFunction> roleFunctions = roleFunctionRepository.findByRoleIdInAndFunctionId(roleIds,
						functionId);
				for (RoleFunction roleFunction : roleFunctions) {
					if (roleFunction.getInsertAuthority() != null && roleFunction.getInsertAuthority() == 1) {
						authorities[0] = true;
					}
					if (roleFunction.getUpdateAuthority() != null && roleFunction.getUpdateAuthority() == 1) {
						authorities[1] = true;
					}
					if (roleFunction.getDeleteAuthority() != null && roleFunction.getDeleteAuthority() == 1) {
						authorities[2] = true;
					}
					if (roleFunction.getOtherAuthority() != null && roleFunction.getOtherAuthority() == 1) {
						authorities[3] = true;
					}
				}
				// 如果用户没有查看的权限，就直接返回
				if (authorities[0] == null || !authorities[0]) {
					handlerReuqest(request, response);
					return false;
				}
				RoleLocal.set(authorities);
			}
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

	private void handlerReuqest(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		boolean isAjax = RequestUtil.isAjax(request);
		// if (isAjax) {
		// ResultVo rv = new ResultVo();
		// rv.setMessage("您没有该操作权限");
		// ResponseBuilder.writeJsonResponse(response, rv);
		// } else {
		response.sendRedirect(request.getContextPath()+"/admin/notAuth");
		// }
	}

}
