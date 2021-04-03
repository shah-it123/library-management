/*
 * 
 */
package com.library.serviceImpl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.SecurityService;

/**
 * The Class SecurityServiceImpl.
 */
@Service
public class SecurityServiceImpl implements SecurityService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/** The user repository. */
	private UserRepository userRepository;
	
	/**
	 * Instantiates a new security service impl.
	 *
	 * @param userRepository the user repository
	 */
	@Autowired
	public SecurityServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Authorizing user with username and password which returns an integer. 
	 * The integer tells us whether  user is authenticated or not.
	 * Return value is 1 if authenticated else 0.
	 *
	 * @author Shahrukh
	 * @param username the username
	 * @param password the password
	 * @return integer
	 */
	@Override
	public int authorizeUser(String username, String password) {
		User user = userRepository.findByUserName(username);
		
		if(user != null) {
			logger.info("authorizeUser " + user.getUserName() + " , " + user.getPassword());
			if(username.equals(user.getUserName()) && BCrypt.checkpw(password, user.getPassword())) {
				logger.info(">>>>>>>>>> Authenticated <<<<<<<<<<");
			}
		}else {
			// Invalid credentials
			logger.info(">>>>>>>>>> NOT Authenticated <<<<<<<<<<");
			return 0;
		}
		
		return 1;
	}

}












