//package com.expect.admin.interceptor;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import com.expect.admin.web.exception.AjaxRequestException;
//
///**
// * Ajax异常处理的类
// */
//public class AjaxExceptionInterceptor {
//
//	@Pointcut(value = "@annotation(com.expect.admin.web.exception.AjaxException)")
//	public void pointAnnotation() {
//	}
//
//	@Around("pointAnnotation()")
//	public Object executeAnnotation(ProceedingJoinPoint pjp) throws AjaxRequestException {
//		try {
//			Object object = pjp.proceed();
//			return object;
//		} catch (Throwable e) {
//			throw new AjaxRequestException();
//		}
//	}
//
//}
