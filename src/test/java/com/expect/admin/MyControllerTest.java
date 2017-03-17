package com.expect.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

//	@Autowired
//	private MockMvc mvc;
	
	@Test
	public void testExample() throws Exception {
//		this.mvc.perform(get("/admin/login").accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
	}

}
