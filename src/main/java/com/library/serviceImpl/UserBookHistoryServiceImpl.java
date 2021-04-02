package com.library.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.UserBookHistory;
import com.library.repository.UserBookHistoryRepository;
import com.library.service.UserBookHistoryService;

@Service
public class UserBookHistoryServiceImpl implements UserBookHistoryService {
	
	@Autowired
	UserBookHistoryRepository userBookHistoryRepository;

	@Override
	public UserBookHistory save(UserBookHistory ubh) {
		return userBookHistoryRepository.save(ubh);
	}

}
