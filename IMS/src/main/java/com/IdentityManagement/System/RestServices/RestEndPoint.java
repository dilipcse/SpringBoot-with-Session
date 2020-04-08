package com.IdentityManagement.System.RestServices;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IdentityManagement.System.Service.CookieHandler;
import com.IdentityManagement.System.Service.TokenGeneration;
import com.IdentityManagement.System.Service.UserRegistrationService;
import com.IdentityManagement.System.model.UserAuth;
import com.IdentityManagement.System.model.UserEntity;

@RestController
public class RestEndPoint {
	
	@Autowired
	private UserRegistrationService userService ;
	@Autowired
	private RedisTemplate<String, Object> redis;
	@Autowired
	private TokenGeneration token_string;
	@Autowired
	private CookieHandler cookieHandle;
	
	public static final String REDIX_INDEX = "user_login";
		
	@PostMapping("/register")		/* register api : user can register with name,password & email*/
	public String registerUser(@RequestBody UserEntity theUser) {
		this.userService.saveUser(theUser);
		return "registered";
	}
	
	@PostMapping("/login")         /* login api : user can login using userName & password  */
	public String loginUser(@RequestBody UserAuth theUser,HttpServletRequest request,HttpServletResponse response) {

		String name= theUser.getUserName();
		String Password = theUser.getPassword();

		UserEntity UserData = userService.findUserByName(name);
		
		if(Password.equals(UserData.getPassword())) {        
			String Token = token_string.getToken();               /*  Random string token of fixed length is generated   */
			Cookie clientCookie = new Cookie("SessionID",Token);  /* cookie created with token  */
			response.addCookie(clientCookie);                     
			redis.opsForHash().put(REDIX_INDEX, name, Token);     /* token is stored on redis server  */
			redis.expire(REDIX_INDEX, 300 , TimeUnit.SECONDS);    /* expiration time set */
			
			return "Successfully Logged in";
		}
		
		return "Incorrect username/password" ;
	}
	
	@GetMapping("/login")								/* relogin api: user can login with token  */
	public String Relogin(@RequestParam String user ,HttpServletRequest request,HttpServletResponse response) {
		
		String redis_token = (String) redis.opsForHash().get("user_login", user);
		Cookie cookieArray[]= request.getCookies();
		
		if(cookieArray != null) {
				
			int token_number = cookieHandle.getTokenNumber(cookieArray);
			Cookie client_token = cookieArray[token_number]; 	
			if(client_token.getValue().equals(redis_token)) {
					redis.expire(REDIX_INDEX, 300 , TimeUnit.SECONDS);
					client_token.setMaxAge(300);
					response.addCookie(client_token);
					return "logged in as "+ user;	
				}
		}
		
		return "login again" ;
	}
	
	@GetMapping("/userDetails/{name}")       /* api : to find user details for logged in user  */
	public UserEntity getUserDetails (@PathVariable String name,HttpServletRequest request, HttpServletResponse response) {
		
		String redis_token = (String) redis.opsForHash().get("user_login", name);
		Cookie cookieArray[]= request.getCookies();
		
		if(cookieArray != null) {             									/* checking if user is logged in*/
				
			int token_number = cookieHandle.getTokenNumber(cookieArray);
			Cookie client_token = cookieArray[token_number]; 	
			if(client_token.getValue().equals(redis_token)) {
					redis.expire(REDIX_INDEX, 300 , TimeUnit.SECONDS);
					client_token.setMaxAge(300);
					response.addCookie(client_token);
					UserEntity UserData = userService.findUserByName(name);   /* found the user details fro the database */
					return UserData;
				}
		}
		
		return null;
	}
	
	@GetMapping("/logout")                    /* logout api: user can logout from the system  */
	public String Userlogout(HttpServletRequest request,HttpServletResponse response) {
		redis.expire(REDIX_INDEX, 0 , TimeUnit.SECONDS);
		Cookie cookieArray[]= request.getCookies();
		
		if(cookieArray != null) {
				for(Cookie cookie : cookieArray) {		
					if(cookie.getName().equals("SessionID")) {		/* Cookie is deleted with token*/
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
		}
		return "Successfully log out";
	}
}
