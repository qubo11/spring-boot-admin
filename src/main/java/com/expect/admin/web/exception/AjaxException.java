package com.expect.admin.web.exception;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标记该方法发生异常时，是Ajax请求的处理方式
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface AjaxException {
	
}
