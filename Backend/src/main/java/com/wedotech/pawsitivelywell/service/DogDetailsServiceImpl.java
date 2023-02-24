package com.wedotech.pawsitivelywell.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		try {
		DogDetails dog = dogRepository.getById(dogId);
		if (dog == null)
			return false;
		UserDetails user = userRepository.getUser(emailId);
		if(user == null)
			return false;
		Set<DogDetails> dogs = user.getDogs();
		dogs.add(dog);
		user.setDogs(dogs);
		userRepository.save(user);
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public DogDetails getDog(Long dogId) {
		return dogRepository.findById(dogId).get();
		/*
		 * DogDetails dog = dogRepository.getById(dogId); if(dog==null) return null;
		 * return dog;
		 */
	}
	
	@Override
	public boolean updateDog(Long dogId, String dogName, int age, String breed, float weight) {
		DogDetails dog = dogRepository.getById(dogId);
		if (dog == null)
			return false;
		dog.setAge(age);
		dog.setDogName(dogName);
		dog.setBreed(breed);
		dog.setWeight(weight);
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
