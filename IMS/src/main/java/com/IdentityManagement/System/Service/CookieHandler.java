package com.IdentityManagement.System.Service;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Service;

@Service
public class CookieHandler {

	public int getTokenNumber(Cookie[] cookieArray) {
		int index = 0;
		for(Cookie tempCookie : cookieArray) {
			if(tempCookie.getName().equals("SessionID")) {
				return index;
			}
			index++;
		}
		return -1;
	}

	
}
