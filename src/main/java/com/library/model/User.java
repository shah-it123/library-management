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

@Entity
@Table(name="users",
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"userName"})
	    })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
						CascadeType.REFRESH})
	private UserBookHistory userBookHistory;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "user",
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,
					CascadeType.DETACH,CascadeType.REFRESH})
	private List<Book> books;
	
	// add convenience methods for bi-directional relationship for Users
	public void add(Book tempBook) {
		if(books == null) {
			books = new ArrayList<Book>();
		}
		
		books.add(tempBook);
		
		tempBook.setUser(this);
	}
	
	
	
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
	@ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
    @JoinColumn(name="created_by")
    private User createdBy;

	@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH},mappedBy="createdBy")
    private Set<User> createdBySet = new HashSet<User>();
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
    @JoinColumn(name="updated_by")
    private User updatedBy;

	@JsonIgnore
    @OneToMany(mappedBy="updatedBy")
    private Set<User> updatedBySet = new HashSet<User>();
	
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
	
	public User() {

	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBookHistory getUserBookHistory() {
		return userBookHistory;
	}

	public void setUserBookHistory(UserBookHistory userBookHistory) {
		this.userBookHistory = userBookHistory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<User> getCreatedBySet() {
		return createdBySet;
	}

	public void setCreatedBySet(Set<User> createdBySet) {
		this.createdBySet = createdBySet;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<User> getUpdatedBySet() {
		return updatedBySet;
	}

	public void setUpdatedBySet(Set<User> updatedBySet) {
		this.updatedBySet = updatedBySet;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", userBookHistory=" + userBookHistory + ", books="
				+ books + "]";
	}

	

	
	
	
	
}
