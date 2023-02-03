package com.wedotech.pawsitivelywell.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dog_details")
public class DogDetails implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dog_id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails userDetails;
	
	@Column(name="DogName")
	private String DogName;
	@Column(name="Breed")
	private String Breed;
	@Column(name="Age")
	private int Age;
	@Column(name="Weight")
	private float Weight;
	@Column(name="Photo")
	private byte[] Photo;
	
	public DogDetails() {
		
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
	

}
