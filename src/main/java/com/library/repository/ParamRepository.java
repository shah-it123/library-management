/*
 * 
 */
package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.ParameterMst;

/**
 * The Interface ParamRepository.
 */
@Repository("paramRepository")
public interface ParamRepository  extends JpaRepository<ParameterMst, Long>{
	
}
