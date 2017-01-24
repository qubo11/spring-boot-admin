package com.expect.admin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.expect.admin.service.impl.UserService;
import com.expect.admin.service.vo.UserVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyTest {

	@Autowired
	private UserService userService;

	@Test
	public void test() {
		UserVo user = userService.getUserById("1");
		assertThat(user.getUsername()).isEqualTo("qubo");
	}

}
