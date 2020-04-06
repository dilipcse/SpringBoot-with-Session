package com.IdentityManagement.System.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.IdentityManagement.System.DAO.RegisterRepository;
import com.IdentityManagement.System.RestServices.RestEndPoint;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestEndPoint.class)
@SpringBootTest
class TestController {
	
	@Autowired
	private MockMvc mockMvc ;
	
	@MockBean
	RegisterRepository regRepo;
	
	@Test
	void registerTest() throws Exception {
		//Mockito.when(regRepo.save(user)).thenReturn();
		String msg = "{\n"+
					 " \"name\" : \"dilip\",\n " +
					 " \"password\" : \"xyz\",\n" +
					 " \"email\" : \"fsa\"\n"+
					 "}";
		mockMvc.perform(
				post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(msg)
				)
				.andExpect(status().isOk())
				.andExpect(content().string("registered"));
	}

}
