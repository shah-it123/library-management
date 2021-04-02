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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "parameter_mst")
public class ParameterMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "description")
	private String description;
	
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
	
	public ParameterMst() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
	
	
}










