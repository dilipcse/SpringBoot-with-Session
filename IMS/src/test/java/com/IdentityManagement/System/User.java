package com.IdentityManagement.System;

public class User {
	String username;
	String password;
	public String getUsername() {
		return username;
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
