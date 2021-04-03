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

@Entity
@Table(name="books", 
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"isbn"})
	    }
)
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="isbn")
	private String isbn;
	
	@Column(name="author")
	private String author;
	
	@JsonIgnore
	@OneToOne(mappedBy = "book", fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
						CascadeType.REFRESH})
	private UserBookHistory userBookHistory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade= {CascadeType.PERSIST,CascadeType.MERGE,
			CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="return_date")
	private Date returnDate;
	
	@Column(name="book_status")
	private String bookStatus;
	
	/*  
	 * TIMESTAMPS START
	 * */
	@JsonIgnore
	@Temporal( TemporalType.TIMESTAMP )
    @CreationTimestamp
	@Column(name = "creation_date")
	private Date creationDate;
	
	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_date")
	private Date updationDate;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="created_by")
	private User createdBy;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="updated_by")
	private User updatedBy;
	
	@PrePersist
	protected void onCreate() {
		creationDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updationDate = new Date();
	}
	
	/*  
	 * TIMESTAMPS END
	 * */
	
	public Book() {

	}

	public Book(String name, String isbn, String author) {
		this.name = name;
		this.isbn = isbn;
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public UserBookHistory getUserBookHistory() {
		return userBookHistory;
	}

	public void setUserBookHistory(UserBookHistory userBookHistory) {
		this.userBookHistory = userBookHistory;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", isbn=" + isbn + ", author=" + author + ", userBookHistory="
				+ userBookHistory + ", user=" + user + ", returnDate=" + returnDate + ", bookStatus=" + bookStatus
				+ "]";
	}

	
	
	
	
	
}





