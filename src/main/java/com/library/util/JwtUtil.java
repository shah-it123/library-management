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

@Service
public class JwtUtil {
	
	private Logger logger = Logger.getLogger(getClass().getName()); 
	
	private String secret = "library_secret_token";
	
	private String username;
	
	private String token;

	public String getToken() {
		return token;
	}
		
	public String getUsername() {
		return username;
	}

	public String extractToken(String token) throws Exception {
		return extractClaim(token, Claims::getSubject);
	}

    public String extractUsername(String token) throws Exception {
    	String retrievedUser = extractToken(token).split(" | ")[0];
    	this.username = retrievedUser;
    	return retrievedUser;
    }

    public Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
    	try {
    		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    	}catch(JwtException | ClassCastException e) {
    		logger.info(">>>>> Exception: " + e.getMessage());
    		throw new JwtException("JWT Token Expired");
    	}
        
    }

    private Boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    @Transactional
    public String generateToken(Authentication auth) {
        Map<String, Object> claims = new HashMap<>();
        
//        return createToken(claims, (String)auth.getPrincipal());
        token = createToken(claims, (String)auth.getPrincipal() + " | " +  auth.getAuthorities());
        logger.info(">>>>> Saved Token " + token);
        return createToken(claims, (String)auth.getPrincipal() + " | " +  auth.getAuthorities());
    }

    private String createToken(Map<String, Object> claims, String subject) {
    	Date expiryAfter = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
    	logger.info("Expire after " + expiryAfter);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryAfter)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
		final String username = extractUsername(token);
		
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
