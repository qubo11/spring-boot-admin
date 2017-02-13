package com.expect.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.expect.admin.web.dialect.ExDialect;

/**
 * 配置thymeleaf的自定义标签
 */
@Configuration
public class ThymeleafDialect {

	@Bean
	public ExDialect exDialect() {
		return new ExDialect();
	}

}
