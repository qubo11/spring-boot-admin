package com.expect.admin.service.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 切点的定义类
 */
@Component
@Aspect
public class InterceptorDefinition {

	@Autowired
	private LogDbInterceptor logDbInterceptor;

	/**
	 * 日志处理的切点，方法一
	 */
	@Pointcut(value = "@annotation(com.expect.admin.service.utils.log.LogDescriptor)")
	public void logPointCut1() {
	}

	/**
	 * 日志处理的切点，方法二
	 */
	@Pointcut(value = "(execution(* *..service.*.save*(..)) && !execution(* com.expect.admin.service.*.save*(..))) || (execution(* *..service.*.update*(..)) && !execution(* com.expect.admin.service.*.update*(..))) || execution(* *..service.*.delete*(..)) && !execution(* com.expect.admin.service.*.delete*(..)))")
	public void logPointCut2() {
	}

	/**
	 * 日志记录通知
	 */
	@Around("logPointCut1()")
	public Object logAdvice(ProceedingJoinPoint pjp) {
		return logDbInterceptor.logPointCut1(pjp);
	}

	// /**
	// * 验证通知
	// */
	// public Object validateAdvice(){
	//
	// }

}
