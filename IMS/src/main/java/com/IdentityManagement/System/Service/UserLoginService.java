package com.IdentityManagement.System.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IdentityManagement.System.DAO.LoginRedisRepository;
import com.IdentityManagement.System.Response.LoginResponse;
import com.IdentityManagement.System.model.UserAuth;
import com.IdentityManagement.System.model.UserEntity;

@Service
public class UserLoginService {


	@Autowired
	private TokenGeneration token_string;
	@Autowired
	private UserRegistrationService userService ;
	@Autowired 
	private LoginRedisRepository redisRepo;
	@Autowired
	private CookieHandler cookieHandle;
	
	public LoginResponse loginUser(UserAuth theUser, HttpServletResponse response) {
		
		String userId= theUser.getUserName();
		String Password = theUser.getPassword();

		UserEntity UserData = userService.findUserByName(userId);
		LoginResponse loginResponse = new LoginResponse("Incorrect userId/password","OK");
		
		if(UserData.getPassword().equals(Password)) {        
			
			String token = token_string.getToken();               /*  Random string token of fixed length is generated   */
			Cookie clientCookie = new Cookie("SessionID",token);  /* cookie created with token  */   
			clientCookie.setMaxAge(3000);
			
			redisRepo.putIntoRedis(token, userId);
			
			response.addCookie(clientCookie);
			loginResponse.setData("Successfully logged in");
		}
		
		return loginResponse;
	}
	
	public LoginResponse reLoginUser(Cookie clientToken,HttpServletResponse response) {
		clientToken.setMaxAge(300);
		response.addCookie(clientToken);
		return new LoginResponse("Successfully logged in","OK");
	}
	
	public UserEntity getUserData(Cookie clientToken, HttpServletResponse response) {
		String result = redisRepo.getFromRedis(clientToken.getValue());
		
		if(result != null)
		{	UserEntity userData = userService.findUserByName(result);
			userData.setPassword("****");
			return userData;
		}
		return null;
	}
	
	public void logoutUser() {
		
	}
	
	public Cookie getSessionCookie(HttpServletRequest request) {
		Cookie cookieArray[]= request.getCookies();
		
		if(cookieArray != null) {
				
			int tokenNumber = cookieHandle.getTokenNumber(cookieArray);
			if(tokenNumber >= 0) {
				Cookie clientToken = cookieArray[tokenNumber]; 
				
				if(validateToken(clientToken.getValue())) {
						return clientToken;
					}
			}
		}
		return null ;
	}
	
	public boolean validateToken(String token) {
		String result = redisRepo.getFromRedis(token);
		
		if(result != null) {
			return true;
		}	
		return false;
	}

	public void getLogoutUser(Cookie clientToken) {
		
		redisRepo.deleteFromRedis(clientToken.getValue());
	}


}