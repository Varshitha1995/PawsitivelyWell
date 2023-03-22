package com.wedotech.pawsitivelywell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wedotech.pawsitivelywell.model.DogDetails;

@Service
public interface DogDetailsService {

	boolean updateDogPhoto(Long dogId, byte[] photo);

	boolean createDog(String dogName, int age, String breed, float weight, String emailId);

	boolean addDog(Long dogId, String emailId);

	boolean updateDog(Long dogId, String dogName, int age, String breed, float weight);

	DogDetails getDog(Long dogId);

	String getFoodRoutine(Long dogId);

	boolean saveFoodRoutine(Long long1, String routine);

	List<String> getTrackedFood(Long dogId);

	boolean trackFood(Long dogId, String data);

	String getRecommendedFood(Long dogId);

	String getActivityRoutine(Long dogId);

	boolean saveActivityRoutine(Long dogId, String routine);

	List<String> getTrackedActivity(Long dogId);

	boolean trackActivity(Long dogId, String data);

	String getRecommendedActivity(Long dogId);

	String getVaccinationRoutine(Long dogId);

	boolean saveVaccinationRoutine(Long dogId, String routine);

	List<String> getTrackedVaccination(Long dogId);

	boolean trackVaccination(Long dogId, String data);

	String getGroomingRoutine(Long dogId);

	boolean saveGroomingRoutine(Long dogId, String routine);

	List<String> getTrackedGrooming(Long dogId);

	boolean trackGrooming(Long dogId, String data);

	String getRecommendedGrooming(Long dogId);


}
