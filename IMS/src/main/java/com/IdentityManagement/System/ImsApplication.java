package com.IdentityManagement.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class ImsApplication extends SpringBootServletInitializer{
	

	public static void main(String[] args) {
		SpringApplication.run(ImsApplication.class, args);
	}

}
