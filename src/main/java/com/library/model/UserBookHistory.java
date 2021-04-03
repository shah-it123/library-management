/*
 * 
 */
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

/**
 * The Class UserBookHistory.
 */
@Entity
@Table(name="user_book_history")
public class UserBookHistory {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The user. */
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	/** The book. */
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="book_id")
	private Book book;
	
	/** The returned date. */
	@Column(name="returnedDate")
	private Date returnedDate;
	
	/**
	 * Instantiates a new user book history.
	 */
	public UserBookHistory() {

	}

	/**
	 * Instantiates a new user book history.
	 *
	 * @param user the user
	 * @param book the book
	 * @param returnedDate the returned date
	 */
	public UserBookHistory(User user, Book book, Date returnedDate) {
		this.user = user;
		this.book = book;
		this.returnedDate = returnedDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the book.
	 *
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * Sets the book.
	 *
	 * @param book the new book
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * Gets the returned date.
	 *
	 * @return the returned date
	 */
	public Date getReturnedDate() {
		return returnedDate;
	}

	/**
	 * Sets the returned date.
	 *
	 * @param returnedDate the new returned date
	 */
	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}
	
	
	
}
