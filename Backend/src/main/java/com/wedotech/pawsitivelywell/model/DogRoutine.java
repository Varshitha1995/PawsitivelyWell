package com.wedotech.pawsitivelywell.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dog_routine")
public class DogRoutine implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "dog_id", nullable = false)
	private DogDetails dog;
	
	@Column(name="FoodRoutine")
	private String foodRoutine;
	
	@Column(name="ActivityRoutine")
	private String activityRoutine;
	
	@Column(name="MedicineRoutine")
	private String medicineRoutine;
	
	@Column(name="VaccinationRoutine")
	private String vaccinationRoutine;
	
	@Column(name="GroomingRoutine")
	private String groomingRoutine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DogDetails getDog() {
		return dog;
	}

	public void setDog(DogDetails dog) {
		this.dog = dog;
	}

	public String getFoodRoutine() {
		return foodRoutine;
	}

	public void setFoodRoutine(String foodRoutine) {
		this.foodRoutine = foodRoutine;
	}

	public String getActivityRoutine() {
		return activityRoutine;
	}

	public void setActivityRoutine(String activityRoutine) {
		this.activityRoutine = activityRoutine;
	}

	public String getMedicineRoutine() {
		return medicineRoutine;
	}

	public void setMedicineRoutine(String medicineRoutine) {
		this.medicineRoutine = medicineRoutine;
	}

	public String getVaccinationRoutine() {
		return vaccinationRoutine;
	}

	public void setVaccinationRoutine(String vaccinationRoutine) {
		this.vaccinationRoutine = vaccinationRoutine;
	}

	public String getGroomingRoutine() {
		return groomingRoutine;
	}

	public void setGroomingRoutine(String groomingRoutine) {
		this.groomingRoutine = groomingRoutine;
	}
	
	
}
