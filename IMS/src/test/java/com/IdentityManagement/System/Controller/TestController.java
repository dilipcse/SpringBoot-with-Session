package com.IdentityManagement.System.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.IdentityManagement.System.RestServices.RestEndPoint;
import com.IdentityManagement.System.Service.CookieHandler;
import com.IdentityManagement.System.Service.TokenGeneration;
import com.IdentityManagement.System.Service.UserRegistrationService;
import com.IdentityManagement.System.model.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestEndPoint.class)
public class TestController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRegistrationService userService ;
	@MockBean
	private RedisTemplate<String, Object> redis;
	@MockBean
	private TokenGeneration token_string;
	@MockBean
	private CookieHandler cookieHandle;
	
	@Test
	public void testRegisterUser() throws Exception {
		UserEntity user = new UserEntity();
		user.setName("Dilip");
		user.setPassword("xyz");
		user.setEmail("dilip@gmail.com");
		
		String inputInJson = this.mapToJson(user);
		String URI = "/register" ;
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo("registered");
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	

	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
