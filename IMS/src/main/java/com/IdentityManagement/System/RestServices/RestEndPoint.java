package com.IdentityManagement.System.RestServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IdentityManagement.System.Response.LoginResponse;
import com.IdentityManagement.System.Response.RegisterResponse;
import com.IdentityManagement.System.Service.UserLoginService;
import com.IdentityManagement.System.Service.UserRegistrationService;
import com.IdentityManagement.System.model.UserAuth;
import com.IdentityManagement.System.model.UserEntity;

@RestController
public class RestEndPoint {
	
	@Autowired
	private UserRegistrationService userService;
	@Autowired
	private UserLoginService userLoginHandler;
		
	@PostMapping("/register")		
	public ResponseEntity<RegisterResponse> registerUser(@RequestBody UserEntity theUser) {
		userService.saveUser(theUser);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new RegisterResponse("Registered","OK"));
	}
		
	@RequestMapping("/login")								
	public ResponseEntity<LoginResponse> Relogin(@RequestBody(required = false) UserAuth theUser ,HttpServletRequest request,HttpServletResponse response) {

		Cookie clientToken = userLoginHandler.getSessionCookie(request); 
				
		if(clientToken != null) {
				LoginResponse result = userLoginHandler.reLoginUser(clientToken,response);
				return ResponseEntity.status(HttpStatus.ACCEPTED)
								.body(result);
		}
		LoginResponse result = userLoginHandler.loginUser(theUser,response);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(result);
	}
	
	@GetMapping("/userDetails")       
	public ResponseEntity<UserEntity> getUserDetails (HttpServletRequest request, HttpServletResponse response) {
		
		Cookie clientToken = userLoginHandler.getSessionCookie(request);
				
		if(clientToken != null) {
				UserEntity user = userLoginHandler.getUserData(clientToken,response);
				return	ResponseEntity.status(HttpStatus.OK)
						.body(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping("/logout")                    
	public ResponseEntity<String> Userlogout(HttpServletRequest request,HttpServletResponse response) {
		
		Cookie clientToken = userLoginHandler.getSessionCookie(request);
		
		if(clientToken != null) {			
				clientToken.setMaxAge(0);
				response.addCookie(clientToken);
				userLoginHandler.getLogoutUser(clientToken);
		}
		return ResponseEntity.status(HttpStatus.OK).body("logged out");
	}
}
