package com.wedotech.pawsitivelywell.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.DogRoutine;
import com.wedotech.pawsitivelywell.model.Tracking;
import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.repository.DogRepository;
import com.wedotech.pawsitivelywell.repository.DogRoutineRepository;
import com.wedotech.pawsitivelywell.repository.DogTrackingRepository;
import com.wedotech.pawsitivelywell.repository.UserRepository;

@Service
public class DogDetailsServiceImpl implements DogDetailsService {

	@Autowired
	DogRepository dogRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	DogRoutineRepository dogRoutineRepository;
	@Autowired
	DogTrackingRepository dogTrackingRepository;

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
		DogDetails dog = dogRepository.findById(dogId).get();
		DogDetails d = new DogDetails(dog.getDog_id(), dog.getDogName(), dog.getBreed(), dog.getAge(), dog.getWeight(), dog.getPhoto());
		return d;
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
	
	@Override
	public String getFoodRoutine(Long dogId) {
		DogRoutine routine = dogRoutineRepository.getRoutine(dogId);
		if(routine == null || routine.getFoodRoutine() == null)
			return null;
		return routine.getFoodRoutine();
	}

	@Override
	public boolean saveFoodRoutine(Long dogId, String routine) {
		DogRoutine dogRoutine = dogRoutineRepository.getRoutine(dogId);
		if(dogRoutine==null) {
			DogDetails dog = dogRepository.getById(dogId);
			if(dog==null)
				return false;
			dogRoutine = new DogRoutine();
			dogRoutine.setDog(dog);
		}
		dogRoutine.setFoodRoutine(routine);
		dogRoutineRepository.save(dogRoutine);
		return true;
		
	}

	@Override
	public List<String> getTrackedFood(Long dogId) {
		List<Tracking> tracking = dogTrackingRepository.getTracking(dogId, "food");
		List<String> foodList = new ArrayList<>();
		for(Tracking food: tracking) {
			foodList.add(food.getDataString());
		}
		return foodList;
	}

	@Override
	public boolean trackFood(Long dogId, String data) {
		Tracking tracking = new Tracking();
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return false;
		tracking.setDog(dog);
		tracking.setType("food");
		tracking.setDataString(data);
		dogTrackingRepository.save(tracking);
		return true;
	}

	@Override
	public String getRecommendedFood(Long dogId) {
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return null;
		JsonObject result = new JsonObject();
		double cups = 0;
		int cals = 0;
		float weight = dog.getWeight();
		if(weight<=3) {
			cups=0.33;
			cals= 139;		
		}else if(weight<=6) {
			cups=0.5;
			cals=233;
		}else if(weight<=10) {
			cups=0.75;
			cals=342;
		}else if(weight<=15) {
			cups=1;
			cals=464;
		}else if(weight<=20) {
			cups=1.33;
			cals=576;
		}else if(weight<=30) {
			cups=1.75;
			cals=781;
		}else if(weight<=40) {
			cups=1.25;
			cals=969;
		}else if(weight<=50) {
			cups=2.66;
			cals=1145;
		}else if(weight<=60) {
			cups=3;
			cals=1313;
		}else if(weight<=70) {
			cups=3.5;
			cals=1474;
		}else if(weight<=80) {
			cups=3.75;
			cals=1629;
		}else if(weight<=90) {
			cups=4.25;
			cals=1779;
		}else {
			cups=4.5;
			cals=1926;
		}
		
		result.addProperty("cups", cups);
		result.addProperty("cals", cals);
		
		return result.toString();
	}

}
