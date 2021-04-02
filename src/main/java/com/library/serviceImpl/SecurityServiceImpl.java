package com.library.serviceImpl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private UserRepository userRepository;
	
	@Autowired
	public SecurityServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

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












