package com.expect.admin.service.log;

import java.lang.reflect.Method;

public class LogUtils {

	/**
	 * 检查是否该类是否需要记录
	 * 
	 * @param className
	 *            签名（类型+方法名）
	 * 
	 * @return true:存在,false:不存在
	 */
	public static boolean check(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			LogDescriptor logDescriptor = clazz.getAnnotation(LogDescriptor.class);
			if (logDescriptor != null) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取日志的描述
	 * 
	 * @param className
	 *            签名（类型+方法名）
	 * 
	 * @return 日志的描述
	 */
	public static LogDescriptor getLogDescriptor(String className, String methodName, int argsLength) {
		try {
			Class<?> clazz = Class.forName(className);
			Method[] methods = clazz.getMethods();
			Method currentMethod = null;
			for (Method method : methods) {
				String methodNameTmp = method.getName();
				if (methodNameTmp.equals(methodName)) {
					int parameterLength = method.getParameterTypes().length;
					if (parameterLength == argsLength) {
						currentMethod = method;
						break;
					}
				}
			}
			if (currentMethod == null) {
				return null;
			}
			LogDescriptor logDescriptor = currentMethod.getAnnotation(LogDescriptor.class);
			return logDescriptor;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
