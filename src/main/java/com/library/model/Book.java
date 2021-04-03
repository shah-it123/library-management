/*
 * 
 */
package com.library.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Book.
 */
@Entity
@Table(name="books", 
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"isbn"})
	    }
)
public class Book {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The name. */
	@Column(name="name")
	private String name;
	
	/** The isbn. */
	@Column(name="isbn")
	private String isbn;
	
	/** The author. */
	@Column(name="author")
	private String author;
	
	/** The user book history. */
	@JsonIgnore
	@OneToOne(mappedBy = "book", fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
						CascadeType.REFRESH})
	private UserBookHistory userBookHistory;
	
	/** The user. */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade= {CascadeType.PERSIST,CascadeType.MERGE,
			CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	/** The return date. */
	@Column(name="return_date")
	private Date returnDate;
	
	/** The book status. */
	@Column(name="book_status")
	private String bookStatus;
	
	/** The creation date. */
	/*  
	 * TIMESTAMPS START
	 * */
	@JsonIgnore
	@Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
	@Column(name = "creation_date")
	private Date creationDate;
	
	/** The updation date. */
	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_date")
	private Date updationDate;
	
	/** The created by. */
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="created_by")
	private User createdBy;
	
	/** The updated by. */
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="updated_by")
	private User updatedBy;
	
	/**
	 * On create.
	 */
	@PrePersist
	protected void onCreate() {
		creationDate = new Date();
	}

	/**
	 * On update.
	 */
	@PreUpdate
	protected void onUpdate() {
		updationDate = new Date();
	}
	
	/*  
	 * TIMESTAMPS END
	 * */
	
	/**
	 * Instantiates a new book.
	 */
	public Book() {

	}

	/**
	 * Instantiates a new book.
	 *
	 * @param name the name
	 * @param isbn the isbn
	 * @param author the author
	 */
	public Book(String name, String isbn, String author) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the book status.
	 *
	 * @return the book status
	 */
	public String getBookStatus() {
		return bookStatus;
	}

	/**
	 * Sets the book status.
	 *
	 * @param bookStatus the new book status
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	/**
	 * Gets the user book history.
	 *
	 * @return the user book history
	 */
	public UserBookHistory getUserBookHistory() {
		return userBookHistory;
	}

	/**
	 * Sets the user book history.
	 *
	 * @param userBookHistory the new user book history
	 */
	public void setUserBookHistory(UserBookHistory userBookHistory) {
		this.userBookHistory = userBookHistory;
	}

	/**
	 * Gets the return date.
	 *
	 * @return the return date
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * Sets the return date.
	 *
	 * @param returnDate the new return date
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
	 * Gets the isbn.
	 *
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Sets the isbn.
	 *
	 * @param isbn the new isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
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
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the updation date.
	 *
	 * @return the updation date
	 */
	public Date getUpdationDate() {
		return updationDate;
	}

	/**
	 * Sets the updation date.
	 *
	 * @param updationDate the new updation date
	 */
	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public User getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", isbn=" + isbn + ", author=" + author + ", userBookHistory="
				+ userBookHistory + ", user=" + user + ", returnDate=" + returnDate + ", bookStatus=" + bookStatus
				+ "]";
	}

	
	
	
	
	
}





