/*
 * 
 */
package com.library.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.model.Role;
import com.library.model.User;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import com.library.util.JwtUtil;
import com.library.util.LibraryUser;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {
	
	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/** The jwt util. */
	@Autowired
	JwtUtil jwtUtil;
	
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/** The role repository. */
	@Autowired
	private RoleRepository roleRepository;
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Instantiates a new user service impl.
	 *
	 * @param userRepository the user repository
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Find by user name.
	 *
	 * @param username the username
	 * @return the user
	 */
	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}
	
	/**
	 * Map roles to authorities.
	 *
	 * @param roles the roles
	 * @return the collection<? extends granted authority>
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	/**
	 * Save.
	 *
	 * @param libraryUser {@link LibraryUser}
	 * @return the string Username
	 */
	@Override
	public String save(LibraryUser libraryUser) {
		User user = new User(libraryUser.getFirstName(), libraryUser.getLastName(), libraryUser.getEmail());
		user.setUserName(libraryUser.getUserName());
		user.setPassword(passwordEncoder.encode(libraryUser.getPassword()));
		
		String loggedInUser = jwtUtil.getUsername();
		
		logger.info(">>>>> Logged In USER " + loggedInUser);
		
		User createdByUser = userRepository.findByUserName(loggedInUser);
		
		user.setCreatedBy(createdByUser);
		
		String[] roleArr = libraryUser.getFormRole().split(",");
		
		List<Role> roleList = new ArrayList<Role>();
		
		for(String r: roleArr) {
			roleList.add(roleRepository.findByName(r));
		}
						
		Collection<Role> roles = roleList;
		
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return user.getUserName();
	}
	
	
	
}



