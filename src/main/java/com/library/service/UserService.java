/*
 * 
 */
package com.library.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.library.model.User;

/**
 * The Interface UserService.
 */
public interface UserService extends UserDetailsService {

	/**
	 * Find by user name.
	 *
	 * @param username the username
	 * @return {@link User}
	 */
	User findByUserName(String username);

}
