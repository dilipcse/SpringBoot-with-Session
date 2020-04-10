package com.IdentityManagement.System.Response;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class LoginResponse {
	
	String data;
	String status;
	
	public LoginResponse( String data, String status) {
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
