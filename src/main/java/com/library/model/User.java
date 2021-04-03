/*
 * 
 */
package com.library.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
 * The Class User.
 */
@Entity
@Table(name="users",
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"userName"})
	    })
public class User {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The user name. */
	@Column(name = "userName")
	private String userName;
	
	/** The password. */
	@Column(name = "password")
	private String password;
	
	/** The first name. */
	@Column(name = "first_name")
	private String firstName;
	
	/** The last name. */
	@Column(name = "last_name")
	private String lastName;
	
	/** The email. */
	@Column(name = "email")
	private String email;
	
	/** The roles. */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	/** The user book history. */
	@JsonIgnore
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
						CascadeType.REFRESH})
	private UserBookHistory userBookHistory;
	
	/** The books. */
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "user",
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,
					CascadeType.DETACH,CascadeType.REFRESH})
	private List<Book> books;
	
	/**
	 * Adds the.
	 *
	 * @param tempBook the temp book
	 */
	// add convenience methods for bi-directional relationship for Users
	public void add(Book tempBook) {
		if(books == null) {
			books = new ArrayList<Book>();
		}
		
		books.add(tempBook);
		
		tempBook.setUser(this);
	}
	
	
	
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
	@ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
    @JoinColumn(name="created_by")
    private User createdBy;

	/** The created by set. */
	@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH},mappedBy="createdBy")
    private Set<User> createdBySet = new HashSet<User>();
	
	/** The updated by. */
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
    @JoinColumn(name="updated_by")
    private User updatedBy;

	/** The updated by set. */
	@JsonIgnore
    @OneToMany(mappedBy="updatedBy")
    private Set<User> updatedBySet = new HashSet<User>();
	
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
	 * Instantiates a new user.
	 */
	public User() {

	}

	/**
	 * Instantiates a new user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 */
	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Collection<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Gets the created by set.
	 *
	 * @return the created by set
	 */
	public Set<User> getCreatedBySet() {
		return createdBySet;
	}

	/**
	 * Sets the created by set.
	 *
	 * @param createdBySet the new created by set
	 */
	public void setCreatedBySet(Set<User> createdBySet) {
		this.createdBySet = createdBySet;
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
	 * Gets the updated by set.
	 *
	 * @return the updated by set
	 */
	public Set<User> getUpdatedBySet() {
		return updatedBySet;
	}

	/**
	 * Sets the updated by set.
	 *
	 * @param updatedBySet the new updated by set
	 */
	public void setUpdatedBySet(Set<User> updatedBySet) {
		this.updatedBySet = updatedBySet;
	}

	/**
	 * Gets the books.
	 *
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Sets the books.
	 *
	 * @param books the new books
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", userBookHistory=" + userBookHistory + ", books="
				+ books + "]";
	}

	

	
	
	
	
}
