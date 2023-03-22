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
	
	@Override
	public String getActivityRoutine(Long dogId) {
		DogRoutine routine = dogRoutineRepository.getRoutine(dogId);
		if(routine == null || routine.getActivityRoutine() == null)
			return null;
		return routine.getActivityRoutine();
	}

	@Override
	public boolean saveActivityRoutine(Long dogId, String routine) {
		DogRoutine dogRoutine = dogRoutineRepository.getRoutine(dogId);
		if(dogRoutine==null) {
			DogDetails dog = dogRepository.getById(dogId);
			if(dog==null)
				return false;
			dogRoutine = new DogRoutine();
			dogRoutine.setDog(dog);
		}
		dogRoutine.setActivityRoutine(routine);
		dogRoutineRepository.save(dogRoutine);
		return true;
		
	}
	
	@Override
	public List<String> getTrackedActivity(Long dogId) {
		List<Tracking> tracking = dogTrackingRepository.getTracking(dogId, "activity");
		List<String> activityList = new ArrayList<>();
		for(Tracking activity: tracking) {
			activityList.add(activity.getDataString());
		}
		return activityList;
	}

	@Override
	public boolean trackActivity(Long dogId, String data) {
		Tracking tracking = new Tracking();
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return false;
		tracking.setDog(dog);
		tracking.setType("activity");
		tracking.setDataString(data);
		dogTrackingRepository.save(tracking);
		return true;
	}


	@Override
	public String getRecommendedActivity(Long dogId) {
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return null;
		JsonObject result = new JsonObject();
		JsonObject activities = getActivities(dog.getBreed());
		int age = dog.getAge();
		if(age<=1) {
			result.addProperty("pup", activities.get("pup").getAsString());
		}else {
		result.addProperty("minutes", activities.get("minutes").getAsNumber());
		result.addProperty("miles", activities.get("miles").getAsNumber());
		}
		return result.toString();
	}

	@Override
	public String getVaccinationRoutine(Long dogId) {
		DogRoutine routine = dogRoutineRepository.getRoutine(dogId);
		if(routine == null || routine.getVaccinationRoutine() == null)
			return null;
		return routine.getVaccinationRoutine();
	}

	@Override
	public boolean saveVaccinationRoutine(Long dogId, String routine) {
		DogRoutine dogRoutine = dogRoutineRepository.getRoutine(dogId);
		if(dogRoutine==null) {
			DogDetails dog = dogRepository.getById(dogId);
			if(dog==null)
				return false;
			dogRoutine = new DogRoutine();
			dogRoutine.setDog(dog);
		}
		dogRoutine.setVaccinationRoutine(routine);
		dogRoutineRepository.save(dogRoutine);
		return true;
		
	}

	@Override
	public List<String> getTrackedVaccination(Long dogId) {
		List<Tracking> tracking = dogTrackingRepository.getTracking(dogId, "vaccine");
		List<String> vaccineList = new ArrayList<>();
		for(Tracking vaccine: tracking) {
			vaccineList.add(vaccine.getDataString());
		}
		return vaccineList;
	}

	@Override
	public boolean trackVaccination(Long dogId, String data) {
		Tracking tracking = new Tracking();
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return false;
		tracking.setDog(dog);
		tracking.setType("vaccine");
		tracking.setDataString(data);
		dogTrackingRepository.save(tracking);
		return true;
	}

	
	private JsonObject getActivities(String breed) {
		JsonObject activityDetails = new JsonObject();
		
		JsonObject activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Affenpinscher", activities);
		
		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Afghan Hound", activities);
		
		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Airedale Terrier", activities);
		
		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Akita", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Alaskan Malamute", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Bulldog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American English Coonhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Eskimo Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Foxhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Hairless Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Leopard Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  70);
		activities.addProperty("miles", 2.8);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Staffordshire Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("American Water Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Anatolian Shepherd Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Appenzeller Sennenhund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Australian Cattle Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Australian Kelpie", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Australian Shepherd", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Australian Stumpy Tail Cattle Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Australian Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Azawakh", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Barbet", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Basenji", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Basset Fauve de Bretagne", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Basset Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bavarian Mountain Scent Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Beagle", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bearded Collie", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Beauceron", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bedlington Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Belgian Laekenois", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Belgian Malinois", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Belgian Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Belgian Tervuren", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bergamasco Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Berger Picard", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bernese Mountain Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bichon Frise", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Biewer Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Black and Tan Coonhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  40);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Black Russian Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bloodhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bluetick Coonhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Boerboel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bohemian Shepherd", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bolognese", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Border Collie", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  75);
		activities.addProperty("miles", 3.25);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Border Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Borzoi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Boston Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bouvier des Flandres", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Boxer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Boykin Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bracco Italiano", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Braque du Bourbonnais", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Braque Francais Pyrenean", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Briard", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Brittany", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Broholmer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Brussels Griffon", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bull Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bulldog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Bullmastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cairn Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Canaan Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cane Corso", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cardigan Welsh Corgi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Carolina Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Catahoula Leopard Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Caucasian Shepherd Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cavalier King Charles Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Central Asian Shepherd Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cesky Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chesapeake Bay Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chihuahua", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chinese Crested", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chinese Shar-Pei", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chinook", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Chow Chow", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cirneco dellEtna", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Clumber Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Cocker Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Collie", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Coton de Tulear", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Croatian Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Curly-Coated Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Czechoslovakian Vlcak", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dachshund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dalmatian", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dandie Dinmont Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Danish-Swedish Farmdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Deutscher Wachtelhund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Doberman Pinscher", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dogo Argentino", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dogue de Bordeaux", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Drentsche Patrijshond", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Drever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Dutch Shepherd", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("English Cocker Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("English Foxhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("English Setter", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("English Springer Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("English Toy Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Entlebucher Mountain Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Estrela Mountain Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Eurasier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Field Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Finnish Lapphund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Finnish Spitz", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Flat-Coated Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("French Bulldog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("French Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Longhaired Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Pinscher", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Shepherd Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Shorthaired Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Spitz", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("German Wirehaired Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Giant Schnauzer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Glen of Imaal Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Golden Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Gordon Setter", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Grand Basset Griffon Vendeen", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Great Dane", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Great Pyrenees", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Greater Swiss Mountain Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Greyhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Hamiltonstovare", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Hanoverian Scenthound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Harrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Havanese", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Hokkaido", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Hovawart", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Ibizan Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Icelandic Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Irish Red and White Setter", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Irish Setter", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Irish Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Irish Water Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Irish Wolfhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Italian Greyhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Jagdterrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Japanese Chin", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Japanese Spitz", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Jindo", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Kai Ken", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Karelian Bear Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Keeshond", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Kerry Blue Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Kishu Ken", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Komondor", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Kromfohrlander", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Kuvasz", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Labrador Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lagotto Romagnolo", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lakeland Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lancashire Heeler", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lapponian Herder", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Leonberger", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lhasa Apso", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Lowchen", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Maltese", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Manchester Terrier (Standard)", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Manchester Terrier (Toy)", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Mastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Miniature American Shepherd", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Miniature Bull Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Miniature Pinscher", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Miniature Schnauzer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Mountain Cur", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Mudi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Neapolitan Mastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Nederlandse Kooikerhondje", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Newfoundland", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norfolk Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norrbottenspets", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norwegian Buhund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norwegian Elkhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norwegian Lundehund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Norwich Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Nova Scotia Duck Tolling Retriever", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Old English Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Otterhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Papillon", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Parson Russell Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pekingese", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pembroke Welsh Corgi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Perro de Presa Canario", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Peruvian Inca Orchid", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Petit Basset Griffon Vendeen", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pharaoh Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Plott Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Polish Lowland Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pomeranian", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Poodle (Miniature)", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Poodle (Standard)", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Poodle (Toy)", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Porcelaine", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Portuguese Podengo", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Portuguese Podengo Pequeno", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Portuguese Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Portuguese Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Portuguese Water Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pudelpointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  45);
		activities.addProperty("miles", 2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pug", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Puli", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pumi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pyrenean Mastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Pyrenean Shepherd", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Rafeiro do Alentejo", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Rat Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Redbone Coonhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Rhodesian Ridgeback", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Romanian Mioritic Shepherd Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Rottweiler", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Russell Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Russian Toy", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Russian Tsvetnaya Bolonka", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Saint Bernard", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Saluki", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Samoyed", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Schapendoes", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Schipperke", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Scottish Deerhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Scottish Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Sealyham Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Segugio Italiano", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Shetland Sheepdog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Shiba Inu", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Shih Tzu", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Shikoku", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Siberian Husky", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Silky Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Skye Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Sloughi", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Slovakian Wirehaired Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Slovensky Cuvac", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Slovensky Kopov", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 3.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Small Munsterlander Pointer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Smooth Fox Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Soft Coated Wheaten Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Spanish Mastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Spanish Water Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Spinone Italiano", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Stabyhoun", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Staffordshire Bull Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Standard Schnauzer", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Sussex Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Swedish Lapphund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 2.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Swedish Vallhund", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  90);
		activities.addProperty("miles", 2.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Taiwan Dog", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Teddy Roosevelt Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 90);
		activities.addProperty("miles", 2.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Thai Ridgeback", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Tibetan Mastiff", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Tibetan Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Tibetan Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 45);
		activities.addProperty("miles", 1.5);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Tornjak", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Tosa", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Toy Fox Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Transylvanian Hound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Treeing Tennessee Brindle", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Treeing Walker Coonhound", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Vizsla", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Weimaraner", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Welsh Springer Spaniel", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Welsh Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("West Highland White Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Wetterhoun", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Whippet", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Wire Fox Terrier", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Wirehaired Pointing Griffon", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 90);
		activities.addProperty("miles", 2.9);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Wirehaired Vizsla", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Working Kelpie", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  60);
		activities.addProperty("miles", 2.6);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Xoloitzcuintli", activities);

		activities = new JsonObject();
		activities.addProperty("minutes",  120);
		activities.addProperty("miles", 5.2);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Yakutian Laika", activities);

		activities = new JsonObject();
		activities.addProperty("minutes", 30);
		activities.addProperty("miles", 1.3);
		activities.addProperty("pup", "1.3 to 2");
		activityDetails.add("Yorkshire Terrier", activities);
		
		return activityDetails.get(breed).getAsJsonObject();
	}

	@Override
	public String getGroomingRoutine(Long dogId) {
		DogRoutine routine = dogRoutineRepository.getRoutine(dogId);
		if(routine == null || routine.getGroomingRoutine() == null)
			return null;
		return routine.getGroomingRoutine();
	}

	@Override
	public boolean saveGroomingRoutine(Long dogId, String routine) {
		DogRoutine dogRoutine = dogRoutineRepository.getRoutine(dogId);
		if(dogRoutine==null) {
			DogDetails dog = dogRepository.getById(dogId);
			if(dog==null)
				return false;
			dogRoutine = new DogRoutine();
			dogRoutine.setDog(dog);
		}
		dogRoutine.setGroomingRoutine(routine);
		dogRoutineRepository.save(dogRoutine);
		return true;
	}

	@Override
	public List<String> getTrackedGrooming(Long dogId) {
		List<Tracking> tracking = dogTrackingRepository.getTracking(dogId, "grooming");
		List<String> groomList = new ArrayList<>();
		for(Tracking groom: tracking) {
			groomList.add(groom.getDataString());
		}
		return groomList;
	}

	@Override
	public boolean trackGrooming(Long dogId, String data) {
		Tracking tracking = new Tracking();
		DogDetails dog = dogRepository.getById(dogId);
		if(dog==null)
			return false;
		tracking.setDog(dog);
		tracking.setType("grooming");
		tracking.setDataString(data);
		dogTrackingRepository.save(tracking);
		return true;
	}

	@Override
	public String getRecommendedGrooming(Long dogId) {
		String breed = dogRepository.getById(dogId).getBreed();
		JsonObject groomingDetails = new JsonObject();
		
		JsonObject grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Affenpinscher", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Afghan Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Airedale Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Akita", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Alaskan Malamute", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("American Bulldog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("American English Coonhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("American Eskimo Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("American Foxhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("American Hairless Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("American Leopard Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("American Staffordshire Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("American Water Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Anatolian Shepherd Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Appenzeller Sennenhund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Australian Cattle Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Australian Kelpie", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Australian Shepherd", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Australian Stumpy Tail Cattle Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Australian Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Azawakh", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Barbet", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Basenji", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Basset Fauve de Bretagne", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Basset Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Bavarian Mountain Scent Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Beagle", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Bearded Collie", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Beauceron", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Bedlington Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Belgian Laekenois", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Belgian Malinois", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Belgian Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Belgian Tervuren", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Bergamasco Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Berger Picard", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Bernese Mountain Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Bichon Frise", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-3");
		groomingDetails.add("Biewer Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Black and Tan Coonhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Black Russian Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Bloodhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Bluetick Coonhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Boerboel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Bohemian Shepherd", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Bolognese", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Border Collie", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Border Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Borzoi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Boston Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Bouvier des Flandres", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Boxer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Boykin Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Bracco Italiano", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Braque du Bourbonnais", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Braque Francais Pyrenean", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Briard", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Brittany", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Broholmer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Brussels Griffon", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Bull Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Bulldog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Bullmastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Cairn Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Canaan Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Cane Corso", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Cardigan Welsh Corgi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Carolina Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Catahoula Leopard Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Caucasian Shepherd Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Cavalier King Charles Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Central Asian Shepherd Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Cesky Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Chesapeake Bay Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Chihuahua", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Chinese Crested", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Chinese Shar-Pei", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Chinook", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Chow Chow", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Cirneco dellEtna", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Clumber Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Cocker Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Collie", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Coton de Tulear", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Croatian Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Curly-Coated Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Czechoslovakian Vlcak", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Dachshund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8");
		groomingDetails.add("Dalmatian", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Dandie Dinmont Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Danish-Swedish Farmdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Deutscher Wachtelhund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Doberman Pinscher", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Dogo Argentino", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Dogue de Bordeaux", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Drentsche Patrijshond", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Drever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Dutch Shepherd", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("English Cocker Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("English Foxhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("English Setter", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("English Springer Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("English Toy Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Entlebucher Mountain Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Estrela Mountain Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Eurasier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Field Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12-16");
		groomingDetails.add("Finnish Lapphund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12-16");
		groomingDetails.add("Finnish Spitz", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Flat-Coated Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("French Bulldog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("French Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("German Longhaired Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("German Pinscher", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("German Shepherd Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("German Shorthaired Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12-16");
		groomingDetails.add("German Spitz", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("German Wirehaired Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Giant Schnauzer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Glen of Imaal Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Golden Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("Gordon Setter", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Grand Basset Griffon Vendeen", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Great Dane", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Great Pyrenees", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Greater Swiss Mountain Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Greyhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Hamiltonstovare", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Hanoverian Scenthound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Harrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Havanese", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Hokkaido", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Hovawart", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Ibizan Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Icelandic Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Irish Red and White Setter", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Irish Setter", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Irish Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Irish Water Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Irish Wolfhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Italian Greyhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Jagdterrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Japanese Chin", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Japanese Spitz", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Jindo", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Kai Ken", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Karelian Bear Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Keeshond", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Kerry Blue Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Kishu Ken", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Komondor", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Kromfohrlander", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Kuvasz", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Labrador Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Lagotto Romagnolo", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Lakeland Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Lancashire Heeler", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Lapponian Herder", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Leonberger", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Lhasa Apso", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Lowchen", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Maltese", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Manchester Terrier (Standard)", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Manchester Terrier (Toy)", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Mastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Miniature American Shepherd", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Miniature Bull Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Miniature Pinscher", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Miniature Schnauzer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Mountain Cur", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Mudi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Neapolitan Mastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Nederlandse Kooikerhondje", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Newfoundland", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Norfolk Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Norrbottenspets", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Norwegian Buhund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Norwegian Elkhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Norwegian Lundehund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Norwich Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Nova Scotia Duck Tolling Retriever", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Old English Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Otterhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Papillon", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Parson Russell Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("Pekingese", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Pembroke Welsh Corgi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Perro de Presa Canario", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Peruvian Inca Orchid", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Petit Basset Griffon Vendeen", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Pharaoh Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Plott Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Polish Lowland Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Pomeranian", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Poodle (Miniature)", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Poodle (Standard)", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Poodle (Toy)", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Porcelaine", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Portuguese Podengo", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Portuguese Podengo Pequeno", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Portuguese Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Portuguese Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Portuguese Water Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Pudelpointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Pug", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Puli", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Pumi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Pyrenean Mastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Pyrenean Shepherd", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Rafeiro do Alentejo", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Rat Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Redbone Coonhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Rhodesian Ridgeback", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Romanian Mioritic Shepherd Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Rottweiler", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Russell Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Russian Toy", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Russian Tsvetnaya Bolonka", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Saint Bernard", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Saluki", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Samoyed", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Schapendoes", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Schipperke", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Scottish Deerhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Scottish Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Sealyham Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Segugio Italiano", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Shetland Sheepdog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("Shiba Inu", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Shih Tzu", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Shikoku", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Siberian Husky", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-5");
		groomingDetails.add("Silky Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-5");
		groomingDetails.add("Skye Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Sloughi", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Slovakian Wirehaired Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Slovensky Cuvac", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Slovensky Kopov", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Borzoi", grooming);
		groomingDetails.add("Small Munsterlander Pointer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Smooth Fox Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Soft Coated Wheaten Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Spanish Mastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Spanish Water Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("Spinone Italiano", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Stabyhoun", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10");
		groomingDetails.add("Staffordshire Bull Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Standard Schnauzer", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Sussex Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12-16");
		groomingDetails.add("Swedish Lapphund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Swedish Vallhund", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Taiwan Dog", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Teddy Roosevelt Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Thai Ridgeback", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Tibetan Mastiff", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Tibetan Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Tibetan Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "12");
		groomingDetails.add("Tornjak", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Tosa", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Toy Fox Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Transylvanian Hound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Treeing Tennessee Brindle", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-12");
		groomingDetails.add("Treeing Walker Coonhound", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-8");
		groomingDetails.add("Vizsla", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Weimaraner", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Welsh Springer Spaniel", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Welsh Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("West Highland White Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "2-4");
		groomingDetails.add("Wetterhoun", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "10-12");
		groomingDetails.add("Whippet", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Wire Fox Terrier", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Wirehaired Pointing Griffon", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "8-10");
		groomingDetails.add("Wirehaired Vizsla", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-10");
		groomingDetails.add("Working Kelpie", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Xoloitzcuintli", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "4-6");
		groomingDetails.add("Yakutian Laika", grooming);
		
		grooming = new JsonObject();
		grooming.addProperty("grooming",  "6-8");
		groomingDetails.add("Yorkshire Terrier", grooming);
		
		return groomingDetails.get(breed).getAsJsonObject().get("grooming").getAsString();
	}
	
	
}
