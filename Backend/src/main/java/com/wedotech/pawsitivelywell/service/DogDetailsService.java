package com.wedotech.pawsitivelywell.service;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;

@Service
public interface DogDetailsService {

	boolean updateDog(Long dogId, JsonObject dogDetails);

	boolean updateDogPhoto(Long dogId, byte[] photo);

	boolean createDog(String dogName, int age, String breed, float weight, String emailId);

	boolean addDog(Long dogId, String emailId);

}
