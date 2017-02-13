package com.expect.admin.utils;

import org.springframework.context.ApplicationContext;

public class SpringUtil {

	private static ApplicationContext applicationContext = null;

	public static void setApplicationContext(ApplicationContext appCtx) {
		applicationContext = appCtx;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
