/*
 * 
 */
package com.library.service;

import java.util.List;

import com.library.exception.ErrorDetails;
import com.library.exception.ResourceNotFoundException;
import com.library.model.ParameterMst;

/**
 * The Interface ParamService.
 */
public interface ParamService {

	/**
	 * Creates the param.
	 *
	 * @param param the param
	 * @return {@link ParameterMst}
	 */
	ParameterMst createParam(ParameterMst param);

	/**
	 * Find by id.
	 *
	 * @param i the id of parameter
	 * @return {@link ParameterMst}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	ParameterMst findById(Long i) throws ResourceNotFoundException;

	/**
	 * Gets the params.
	 *
	 * @return List of {@link ParameterMst}
	 */
	List<ParameterMst> getParams();

}
