package com.wedotech.pawsitivelywell.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_details")
public class UserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_id;
	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Column(name="FirstName")
	private String FirstName;
	@Column(name="LastName")
	private String LastName;
	@Column(name="EmailId")
	private String EmailId;
	@Column(name="Password")
	private String Password;
	@OneToMany(mappedBy="userDetails", cascade= CascadeType.DETACH, fetch = FetchType.EAGER)
	private List<DogDetails> Dogs;
	
	public UserDetails() {
		
	}
	
	public UserDetails(String firstName, String lastName, String emailId, String password) {
		super();
		FirstName = firstName;
		LastName = lastName;
		EmailId = emailId;
		this.Password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public List<DogDetails> getDogs() {
		return Dogs;
	}

	public void setDogs(List<DogDetails> dogs) {
		Dogs = dogs;
	}

	
	
	

}
