package com.expect.admin.service.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 日志记录描述注解，用于日志注解的AOP
 * 
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface LogDescriptor {

	public final String OperationType_Insert = "Insert";
	public final String OperationType_Update = "Update";
	public final String OperationType_Delete = "Delete";
	public final String OperationType_InsertOrUpdate = "InsertOrUpdate";

	/**
	 * 日志描述
	 */
	public String value() default "";

	/**
	 * 日志操作类型
	 */
	public String operationType() default "";

}
