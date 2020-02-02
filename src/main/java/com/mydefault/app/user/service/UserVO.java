package com.mydefault.app.user.service;

import com.mydefault.app.common.util.CommonVO;

public class UserVO extends CommonVO {

	private static final long serialVersionUID = 5L;
	
	private String username = "";
	
	private String password = "";
	
	private String token = "";

	public String getUsername() {
		return username;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
