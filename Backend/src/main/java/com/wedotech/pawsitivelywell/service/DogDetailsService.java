package com.wedotech.pawsitivelywell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
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

}
