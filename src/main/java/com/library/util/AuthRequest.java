package com.library.util;

public class AuthRequest {
	private String username;
	
	private String password;
	
	private String oldPassword;
	
	public AuthRequest() {
		
	}

	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

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

	@Override
	public String toString() {
		return "AuthRequest [username=" + username + ", password= ***** ]";
	}
	
	
}
