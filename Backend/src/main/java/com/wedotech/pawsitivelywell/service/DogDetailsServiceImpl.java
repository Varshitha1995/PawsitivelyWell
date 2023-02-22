package com.wedotech.pawsitivelywell.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.repository.DogRepository;
import com.wedotech.pawsitivelywell.repository.UserRepository;

@Service
public class DogDetailsServiceImpl implements DogDetailsService {

	@Autowired
	DogRepository dogRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public boolean createDog(String dogName, int age, String breed, float weight, String emailId) {
		DogDetails dog = new DogDetails(dogName, breed, age, weight);
		UserDetails user = userRepository.getUser(emailId);
		dogRepository.save(dog);
		Set<DogDetails> dogs = user.getDogs();
		dogs.add(dog);
		user.setDogs(dogs);
		userRepository.save(user);
		return true;
	}
	
	@Override
	public boolean addDog(Long dogId, String emailId) {
		DogDetails dog = dogRepository.getById(dogId);
		UserDetails user = userRepository.getUser(emailId);
		Set<DogDetails> dogs = user.getDogs();
		dogs.add(dog);
		user.setDogs(dogs);
		userRepository.save(user);
		return true;
	}
	
	@Override
	public boolean updateDog(Long dogId, JsonObject dogDetails) {
		DogDetails dog = dogRepository.getById(dogId);
		if (dog == null)
			return false;
		dog.setAge(dogDetails.get("age").getAsInt());
		dog.setDogName(dogDetails.get("name").getAsString());
		dog.setBreed(dogDetails.get("breed").getAsString());
		dog.setWeight(dogDetails.get("weight").getAsFloat());
		dogRepository.save(dog);
		return true;
	}

	@Override
	public boolean updateDogPhoto(Long dogId, byte[] photo) {
		DogDetails dog = dogRepository.getById(dogId);
		if (dog == null)
			return false;
		dog.setPhoto(photo);
		dogRepository.save(dog);
		return true;
	}

}
