package com.library.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.ParameterMst;
import com.library.repository.ParamRepository;
import com.library.service.ParamService;

@Service
public class ParamServiceimpl implements ParamService {
	
	private ParamRepository paramRepository;
	
	@Autowired
	public ParamServiceimpl(ParamRepository paramRepository) {
		this.paramRepository = paramRepository;
	}

	@Override
	public ParameterMst createParam(ParameterMst param) {
		return paramRepository.save(param);
	}

	@Override
	public ParameterMst findById(Long i) {
		return paramRepository.findById(i).get();
	}

	@Override
	public List<ParameterMst> getParams() {
		return paramRepository.findAll();
	}

}
