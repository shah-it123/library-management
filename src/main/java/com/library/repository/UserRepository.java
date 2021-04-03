/*
 * 
 */
package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.User;

/**
 * The Interface UserRepository.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * Find by user name.
	 *
	 * @param username the username
	 * @return {@link User}
	 */
	User findByUserName(String username);
	
	

}
