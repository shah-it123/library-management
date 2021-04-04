/*
 * 
 */
package com.library.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.library.model.User;
import com.library.util.LibraryUser;

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

	/**
	 * Save.
	 *
	 * @param libraryUser {@link LibraryUser}
	 * @return the string Username
	 */
	String save(LibraryUser libraryUser);

}
