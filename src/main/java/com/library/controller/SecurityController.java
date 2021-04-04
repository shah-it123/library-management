/*
 * 
 */
package com.library.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.util.AuthRequest;
import com.library.util.JwtUtil;

/**
 * Security Rest Controller.
 */
@RestController
public class SecurityController {
	
	/** The jwtutil. */
	@Autowired
	private JwtUtil jwtutil;
	
	/** The authentication provider. */
	@Autowired
	@Qualifier("customAuthenticationProvider")
	private AuthenticationProvider authenticationProvider;
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName()); 
	
	
	/**
	 * Authenticate.
	 *
	 * @param request the request
	 * @param authRequest the auth request
	 * @return the string
	 * @throws Exception the exception
	 */
	@PostMapping("/authenticate")
	public String authenticate(HttpServletRequest request, @RequestBody AuthRequest authRequest) throws Exception {
		
		logger.info(authRequest.toString()); 
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());
		
		logger.info(">>>>>DETAILS " + new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
		
		usernamePasswordAuthenticationToken.setDetails(
				new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
		try { 
			Authentication auth = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
			logger.info("REST AUTH RECEIVED " + auth);
			return jwtutil.getToken();
		}catch(Exception e) {
			logger.info(">>>>>>>>>>>> EXCEPTION " + e.getMessage());
			
			return e.getMessage();
		}
				
	}
}
