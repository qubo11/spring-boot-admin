package com.expect.admin.web.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标记管理页面的注解
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PageEnter {

}
