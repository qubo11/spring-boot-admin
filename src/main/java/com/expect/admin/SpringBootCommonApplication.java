package com.expect.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.expect.admin.data.support.repository.CustomRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class SpringBootCommonApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCommonApplication.class, args);
	}
	
}
