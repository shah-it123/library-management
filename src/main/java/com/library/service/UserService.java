package com.library.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.library.model.User;

public interface UserService extends UserDetailsService {

	User findByUserName(String username);

}
