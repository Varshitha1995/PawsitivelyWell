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
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "dog_details")
public class DogDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dog_id;

//	@ManyToMany(mappedBy = "dogs", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
////	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
////	@JoinTable(name = "user_dog", inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), joinColumns = @JoinColumn(name = "dog_id", referencedColumnName = "dog_id"))
//	private Set<UserDetails> users = new HashSet<>();

	@Column(name = "DogName")
	private String DogName;
	@Column(name = "Breed")
	private String Breed;
	@Column(name = "Age")
	private int Age;
	@Column(name = "Weight")
	private float Weight;
	@Column(name = "Photo")
	private byte[] Photo;
	
	@OneToOne(mappedBy="dog", cascade= CascadeType.DETACH, fetch = FetchType.LAZY)
	private DogRoutine routine;
	
	@OneToMany(mappedBy="dog", cascade= CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Tracking> tracking;

	public DogDetails() {

	}

	public DogDetails(String dogName, String breed, int age, float weight) {
		super();
		DogName = dogName;
		Breed = breed;
		Age = age;
		Weight = weight;
	}

	public DogDetails(Long id, String dogName, String breed, int age, float weight, byte[] photo) {
		super();
		dog_id = id;
		DogName = dogName;
		Breed = breed;
		Age = age;
		Weight = weight;
		Photo = photo;
	}
	
	public Long getDog_id() {
		return dog_id;
	}

	public void setDog_id(Long dog_id) {
		this.dog_id = dog_id;
	}

	public DogDetails(String dogName, String breed, int age, float weight, byte[] photo) {
		super();
		DogName = dogName;
		Breed = breed;
		Age = age;
		Weight = weight;
		Photo = photo;
	}
	
	public String getDogName() {
		return DogName;
	}

	public void setDogName(String dogName) {
		DogName = dogName;
	}

	public String getBreed() {
		return Breed;
	}

	public void setBreed(String breed) {
		Breed = breed;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public float getWeight() {
		return Weight;
	}

	public void setWeight(float weight) {
		Weight = weight;
	}

	public byte[] getPhoto() {
		return Photo;
	}

	public void setPhoto(byte[] photo) {
		Photo = photo;
	}
	
	public DogRoutine getRoutine() {
		return routine;
	}

	public void setRoutine(DogRoutine routine) {
		this.routine = routine;
	}

	public List<Tracking> getTracking() {
		return tracking;
	}

	public void setTracking(List<Tracking> tracking) {
		this.tracking = tracking;
	}

//	public Set<UserDetails> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<UserDetails> users) {
//		this.users = users;
//	}

}
