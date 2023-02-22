package com.wedotech.pawsitivelywell.model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

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

	@Column(name = "FirstName")
	private String FirstName;
	@Column(name = "LastName")
	private String LastName;
	@Column(name = "emailId")
	private String emailId;
	@Column(name = "Password")
	private String Password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_dog", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "dog_id"))
	private Set<DogDetails> dogs = new HashSet<>();

	public UserDetails() {

	}

	public UserDetails(String firstName, String lastName, String email, String password) {
		super();
		FirstName = firstName;
		LastName = lastName;
		emailId = email;
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
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public Set<DogDetails> getDogs() {
		return dogs;
	}

	public void setDogs(Set<DogDetails> dogs) {
		this.dogs = dogs;
	}

}
