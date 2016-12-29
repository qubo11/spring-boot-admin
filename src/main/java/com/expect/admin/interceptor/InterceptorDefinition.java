package com.expect.admin.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 切点的定义类
 */
@Component
@Aspect
public class InterceptorDefinition {

	@Autowired
	private LogDbInterceptor logDbInterceptor;

	/**
	 * 异常处理的切点
	 */
	@Pointcut(value = "@annotation(com.expect.admin.web.exception.AjaxException)")
	public void ajaxPointCut() {
	}

	/**
	 * 日志处理的切点，方法一
	 */
	@Pointcut(value = "@annotation(com.expect.admin.service.log.LogDescriptor)")
	public void logPointCut1() {
	}

	/**
	 * 日志处理的切点，方法二
	 */
	@Pointcut(value = "(execution(* *..service.*.save*(..)) && !execution(* com.expect.admin.service.*.save*(..))) || (execution(* *..service.*.update*(..)) && !execution(* com.expect.admin.service.*.update*(..))) || execution(* *..service.*.delete*(..)) && !execution(* com.expect.admin.service.*.delete*(..)))")
	public void logPointCut2() {
	}

	/**
	 * 异常处理通知
	 */
	@Around("ajaxPointCut()")
	public Object ajaxAdvice(ProceedingJoinPoint pjp) throws AjaxRequestException {
		try {
			Object object = pjp.proceed();
			return object;
		} catch (Throwable e) {
			throw new AjaxRequestException();
		}
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
