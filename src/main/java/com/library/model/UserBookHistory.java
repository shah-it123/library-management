package com.library.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_book_history")
public class UserBookHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="book_id")
	private Book book;
	
	@Column(name="returnedDate")
	private Date returnedDate;
	
	public UserBookHistory() {

	}

	public UserBookHistory(User user, Book book, Date returnedDate) {
		this.user = user;
		this.book = book;
		this.returnedDate = returnedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}
	
	
	
}
