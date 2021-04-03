/*
 * 
 */
package com.library.service;

/**
 * The Interface SecurityService.
 */
public interface SecurityService {

	/**
	 * Authorize user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return integer
	 */
	public int authorizeUser(String username, String password);

}
