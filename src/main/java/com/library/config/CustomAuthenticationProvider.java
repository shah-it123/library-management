/*
 * 
 */
package com.library.config;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.library.model.Role;
import com.library.model.User;
import com.library.service.SecurityService;
import com.library.service.UserService;
import com.library.util.JwtUtil;

// TODO: Auto-generated Javadoc
/**
 * Implementing Custom Authentication.
 *
 * @author Shahrukh
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/** The security service. */
	@Autowired
	private SecurityService securityService;
	
	/** The user service. */
	@Autowired
	private UserService userService;
	
	/** The jwtutil. */
	@Autowired
	private JwtUtil jwtutil;
	
	/**
	 * Overriding of authentication method to provide custom authentication.
	 *
	 * @param authentication the authentication
	 * @return {@link Authentication}
	 * @throws AuthenticationException the authentication exception
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("IN AUTHENTICATE");
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		int access_condition= securityService.authorizeUser(username, password);
		
		logger.info(">>>>> access_condition " + access_condition);
		
		switch(access_condition) {
			case 1:	
				User user = userService.findByUserName(username);
				
				logger.info(">>>>>>> ALL ACCESS " + mapRolesToAuthorities(user.getRoles()));
	            
	            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
	            		username, password, 
	            		mapRolesToAuthorities(user.getRoles()));
	            
	            userAuthToken.setDetails(authentication.getDetails());
	            
	            Authentication auth = userAuthToken;
	            
	            logger.info(">>>>>>>>>>> auth " + auth);
	            
	            /**
	             * Generate Authentication token and send it to the user
	             */
	            jwtutil.generateToken(auth);
	            
	            return auth;
			case -1:
				throw new AuthenticationCredentialsNotFoundException("Username not found!");
			default:
				throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
		}
	}
	
	/**
	 * Maps Roles to Authorities and assigns them to User.
	 *
	 * @param roles the roles
	 * @return the collection<? extends granted authority>
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	/**
	 * Supports.
	 *
	 * @param authentication the authentication
	 * @return true, if successful
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}











