package com.expect.admin.service.validation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表单验证的注解
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Validate {

	/**
	 * 验证的返回提示
	 */
	public Class<?>[] value() default {};

}
