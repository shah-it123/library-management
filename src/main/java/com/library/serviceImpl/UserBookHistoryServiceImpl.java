/*
 * 
 */
package com.library.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.UserBookHistory;
import com.library.repository.UserBookHistoryRepository;
import com.library.service.UserBookHistoryService;

/**
 * The Class UserBookHistoryServiceImpl.
 */
@Service
public class UserBookHistoryServiceImpl implements UserBookHistoryService {
	
	/** The user book history repository. */
	@Autowired
	UserBookHistoryRepository userBookHistoryRepository;

	/**
	 * Save.
	 *
	 * @param ubh the ubh
	 * @return the user book history
	 */
	@Override
	public UserBookHistory save(UserBookHistory ubh) {
		return userBookHistoryRepository.save(ubh);
	}

}
