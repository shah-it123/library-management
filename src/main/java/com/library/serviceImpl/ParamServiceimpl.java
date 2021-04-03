/*
 * 
 */
package com.library.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exception.ResourceNotFoundException;
import com.library.model.ParameterMst;
import com.library.repository.ParamRepository;
import com.library.service.ParamService;

/**
 * The Class ParamServiceimpl.
 */
@Service
public class ParamServiceimpl implements ParamService {
	
	/** The param repository. */
	private ParamRepository paramRepository;
	
	/**
	 * Instantiates a new param serviceimpl.
	 *
	 * @param paramRepository the param repository
	 */
	@Autowired
	public ParamServiceimpl(ParamRepository paramRepository) {
		this.paramRepository = paramRepository;
	}

	/**
	 * Creates the param.
	 *
	 * @param param the param
	 * @return the parameter mst
	 */
	@Override
	public ParameterMst createParam(ParameterMst param) {
		return paramRepository.save(param);
	}

	/**
	 * Find by id.
	 *
	 * @param i the i
	 * @return the parameter mst
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@Override
	public ParameterMst findById(Long i) throws ResourceNotFoundException {
		return paramRepository.findById(i)
				.orElseThrow(() -> new ResourceNotFoundException("Parameter not found for this id :: " + i));
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	@Override
	public List<ParameterMst> getParams() {
		return paramRepository.findAll();
	}

}
