/*
 * 
 */
package com.library.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class JwtUtil.
 */
@Service
public class JwtUtil {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName()); 
	
	/** The secret. */
	private String secret = "library_secret_token";
	
	/** The username. */
	private String username;
	
	/** The token. */
	private String token;

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
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
	 * Extract token.
	 *
	 * @param token the token
	 * @return the string
	 * @throws Exception the exception
	 */
	public String extractToken(String token) throws Exception {
		return extractClaim(token, Claims::getSubject);
	}

    /**
     * Extract username.
     *
     * @param token the token
     * @return the string
     * @throws Exception the exception
     */
    public String extractUsername(String token) throws Exception {
    	String retrievedUser = extractToken(token).split(" | ")[0];
    	this.username = retrievedUser;
    	return retrievedUser;
    }

    /**
     * Extract expiration.
     *
     * @param token the token
     * @return the date
     * @throws Exception the exception
     */
    public Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract claim.
     *
     * @param <T> the generic type
     * @param token the token
     * @param claimsResolver the claims resolver
     * @return the t
     * @throws Exception the exception
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extract all claims.
     *
     * @param token the token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
    	try {
    		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    	}catch(JwtException | ClassCastException e) {
    		logger.info(">>>>> Exception: " + e.getMessage());
    		throw new JwtException("JWT Token Expired");
    	}
        
    }

    /**
     * Checks if is token expired.
     *
     * @param token the token
     * @return the boolean
     * @throws Exception the exception
     */
    private Boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generate token.
     *
     * @param auth the auth
     * @return the string
     */
    @Transactional
    public String generateToken(Authentication auth) {
        Map<String, Object> claims = new HashMap<>();
        
//        return createToken(claims, (String)auth.getPrincipal());
        token = createToken(claims, (String)auth.getPrincipal() + " | " +  auth.getAuthorities());
        logger.info(">>>>> Saved Token " + token);
        return createToken(claims, (String)auth.getPrincipal() + " | " +  auth.getAuthorities());
    }

    /**
     * Creates the token.
     *
     * @param claims the claims
     * @param subject the subject
     * @return the string
     */
    private String createToken(Map<String, Object> claims, String subject) {
    	Date expiryAfter = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
    	logger.info("Expire after " + expiryAfter);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryAfter)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
     * Validate token.
     *
     * @param token the token
     * @param userDetails the user details
     * @return the boolean
     * @throws Exception the exception
     */
    public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
		final String username = extractUsername(token);
		
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
