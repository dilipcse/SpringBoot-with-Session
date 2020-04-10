package com.IdentityManagement.System.Response;

public class RegisterResponse {
	String data;
	String status;
	
	public RegisterResponse( String data, String status) {
		this.data = data;
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
