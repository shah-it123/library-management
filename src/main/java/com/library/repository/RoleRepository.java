package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.Role;

/**
 * The Interface RoleRepository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the role {@link Role}
	 */
	Role findByName(String name);
}
