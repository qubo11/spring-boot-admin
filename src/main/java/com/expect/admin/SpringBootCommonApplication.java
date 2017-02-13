package com.expect.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.expect.admin.data.support.repository.CustomRepositoryFactoryBean;
import com.expect.admin.utils.SpringUtil;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
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
