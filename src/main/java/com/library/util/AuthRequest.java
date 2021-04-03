/*
 * 
 */
package com.library.util;

/**
 * The Class AuthRequest.
 */
public class AuthRequest {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The old password. */
	private String oldPassword;
	
	/**
	 * Instantiates a new auth request.
	 */
	public AuthRequest() {
		
	}

	/**
	 * Instantiates a new auth request.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	

	/**
	 * Gets the old password.
	 *
	 * @return the old password
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * Sets the old password.
	 *
	 * @param oldPassword the new old password
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "AuthRequest [username=" + username + ", password= ***** ]";
	}
	
	
}
