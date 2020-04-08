package com.IdentityManagement.System.model;

public class UserAuth {

	String userName;
	String password;
	
	public UserAuth() {
	}
	
	public UserAuth(String userName, String password) {
		System.out.println("const called");
		this.userName = userName;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		System.out.println("passowrd called "+password);
		this.password = password;
	}
	
}
