package com.expect.admin.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import com.expect.admin.service.impl.custom.UserService;
import com.expect.admin.service.impl.custom.UserService.LoginSuccessHandler;

/**
 * 权限验证配置
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		auth.eraseCredentials(false);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 配置session
//		http.sessionManagement().invalidSessionUrl("/admin/sessionTimeout");

		// 头部配置
		http.headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();
		// csrf关闭
		http.csrf().disable();
		// 登录配置
		http.formLogin().loginPage("/admin/login").failureUrl("/admin/login?error").successHandler(loginSuccessHandler()).permitAll()
				.and().logout().logoutUrl("/admin/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.permitAll().and().rememberMe();
//		.tokenValiditySeconds(1209600).tokenRepository(tokenRepository())

		// 请求配置，只有/admin开头的url才需要验证
		http.authorizeRequests().antMatchers("/admin/**").authenticated();
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return userService.new LoginSuccessHandler();
	}

	@Bean
	public JdbcTokenRepositoryImpl tokenRepository() {
		JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
		jtr.setDataSource(dataSource);
		return jtr;
	}

}