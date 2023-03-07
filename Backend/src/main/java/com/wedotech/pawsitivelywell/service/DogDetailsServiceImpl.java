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
}
