package com.wedotech.pawsitivelywell.service;

import org.springframework.stereotype.Service;

@Service
public interface DogDetailsService {

	boolean updateDogPhoto(Long dogId, byte[] photo);

	boolean createDog(String dogName, int age, String breed, float weight, String emailId);

	boolean addDog(Long dogId, String emailId);

	boolean updateDog(Long dogId, String dogName, int age, String breed, float weight);

}
