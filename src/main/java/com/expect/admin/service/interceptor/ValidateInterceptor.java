package com.expect.admin.service.interceptor;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.expect.admin.service.utils.validate.Validate;

@Component
public class ValidateInterceptor {

	public Object validateAdvice(ProceedingJoinPoint pjp) throws Exception {
		// 类名
		String className = pjp.getSignature().getDeclaringTypeName();
		// 方法名
		String methodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		Class<?> clazz = Class.forName(className);
		if (clazz != null) {
			Method currentMethod = null;
			Method[] methods = clazz.getDeclaredMethods();
			// 获取当前访问的方法
			for (Method method : methods) {
				String oMethodName = method.getName();
				// 方法名一样
				if (oMethodName.equals(methodName)) {
					Class<?>[] paramterTypes = method.getParameterTypes();
					// 判断参数个数是否相同
					if (paramterTypes != null) {
						if (paramterTypes.length == args.length) {
							currentMethod = method;
						}
					} else {
						if (args == null) {
							currentMethod = method;
						}
					}
				}
			}
			if (currentMethod != null) {
				//判断是否有Validate注解
				Validate validate = currentMethod.getAnnotation(Validate.class);
				if (validate != null) {
				}
			}
		}
		return null;
	}

}
