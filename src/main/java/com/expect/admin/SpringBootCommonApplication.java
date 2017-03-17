package com.expect.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.expect.admin.data.support.repository.CustomRepositoryFactoryBean;
import com.expect.custom.utils.SpringUtil;

@SpringBootApplication
@EntityScan(basePackages = { "com.expect.admin.data.dataobject", "com.expect.custom.data.dataobject" })
@ComponentScan(basePackages = { "com.expect" })
@EnableJpaRepositories(basePackages = { "com.expect.admin.data.dao",
		"com.expect.custom.data.dao" }, repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class SpringBootCommonApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		final ApplicationContext appCtx = SpringApplication.run(SpringBootCommonApplication.class, args);
		SpringUtil.setApplicationContext(appCtx);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootCommonApplication.class);
	}
}
