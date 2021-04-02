package com.library.service;

import java.util.List;

import com.library.model.ParameterMst;

public interface ParamService {

	ParameterMst createParam(ParameterMst param);

	ParameterMst findById(Long i);

	List<ParameterMst> getParams();

}