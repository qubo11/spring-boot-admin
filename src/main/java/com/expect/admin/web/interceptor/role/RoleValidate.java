package com.expect.admin.web.interceptor.role;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 权限验证注解，用于权限验证注解的AOP
 * 
 * 需要验证权限的的请求，需要加上functionId才行
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface RoleValidate {

}
