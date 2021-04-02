package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.UserBookHistory;

@Repository
public interface UserBookHistoryRepository  extends JpaRepository<UserBookHistory, Long> {

}
