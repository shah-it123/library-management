/*
 * 
 */
package com.library.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.library.service.UserService;
import com.library.util.JwtUtil;

/**
 * The Class JwtFilter.
 */
@Component
public class JwtFilter  extends OncePerRequestFilter {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName()); 
	
	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;
	
	/** The service. */
	@Autowired
	UserService service;

	/**
	 * Do filter internal.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterChain the filter chain
	 * @throws ServletException {@link ServletException}
	 * @throws IOException {@link IOException} Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		String token=null;
		String username=null;
		
		logger.info("authorizationHeader " + authorizationHeader);
		try {
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				logger.info(">>>>> token " + token);
								
				username=jwtUtil.extractUsername(token);
							
				logger.info(">>>>> username " + username);
			}
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = service.loadUserByUsername(username);
				if (jwtUtil.validateToken(token, userDetails)) {

	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
			}
		}catch(Exception e) {
			logger.info(">>>> Exception is " + e.getMessage());
		}
		
		
		filterChain.doFilter(request, response);
		
	}
}