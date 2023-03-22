package com.wedotech.pawsitivelywell.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.service.DogDetailsService;
import com.wedotech.pawsitivelywell.service.InformationService;
import com.wedotech.pawsitivelywell.service.UserDetailsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PawsitivelyWellController {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	DogDetailsService dogDetailsService;
	@Autowired
	InformationService informationService;

	@PostMapping("user/login")
	public boolean validateLogin(@RequestParam String emailId, @RequestParam String password) {
		return userDetailsService.validateLogin(emailId, password);
	}

	@PostMapping("user/signup")
	public boolean createUser(@RequestPart String firstName, @RequestPart String lastName, @RequestPart String emailId,
			@RequestPart String password) {
		return userDetailsService.createUser(firstName, lastName, emailId, password);
	}

	@GetMapping("user/getdogs")
	public Set<DogDetails> getDogsByUser(@RequestParam String emailId) {
		return userDetailsService.getDogsByEmail(emailId);
	}
	
	@GetMapping("user/getDetails")
	public UserDetails getUserDetails(@RequestParam String emailId) {
		return userDetailsService.getUserDetails(emailId);
	}

	@PostMapping("user/updateUser")
	public boolean updateUser(@RequestPart String firstName, @RequestPart String lastName, @RequestPart String emailId,
			@RequestPart String password) {
		return userDetailsService.updateUser(emailId, firstName, lastName, password);
	}
	
	@PostMapping("user/removeDog")
	public boolean removeDog(@RequestPart String emailId, @RequestPart String dogId) {
		return userDetailsService.removeDog(emailId, new Long(dogId));
	}
	
	@PostMapping("dog/createDog")
	public boolean createDog(@RequestPart String dogName,@RequestPart String age,@RequestPart String breed,@RequestPart String weight,@RequestPart String emailId) {
		return dogDetailsService.createDog(dogName, new Integer(age), breed, new Float(weight), emailId);
	}
	
	@PostMapping("dog/addDog")
	public boolean addDog(@RequestPart String dogId, @RequestPart String emailId) {
		return dogDetailsService.addDog(new Long(dogId), emailId);
	}
	
	@GetMapping("dog/getDog")
	public DogDetails getDog(@RequestParam String dogId) {
		return dogDetailsService.getDog(new Long(dogId));
	}
	
	@PostMapping("dog/updateDog")
	public boolean updateDog(@RequestPart String dogId, @RequestPart String dogName, @RequestPart String age, @RequestPart String breed, @RequestPart String weight) {
		return dogDetailsService.updateDog(new Long(dogId), dogName, new Integer(age), breed, new Float(weight));
	}
	
	@PostMapping("dog/uploadPhoto")
	public boolean uploadPhoto(@RequestPart String dogId, @RequestPart MultipartFile photo) {
		BufferedImage originalImage;
		byte[] imageInByte = null;
		try {
			originalImage = ImageIO.read(photo.getInputStream());
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			imageInByte=baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dogDetailsService.updateDogPhoto(new Long(dogId), imageInByte);
		
	}
	
	@GetMapping("dog/getFoodRoutine")
	public String getFoodRoutine(@RequestParam String dogId) {
		return dogDetailsService.getFoodRoutine(new Long(dogId));
	}
	
	@PostMapping("dog/saveFoodRoutine")
	public boolean saveFoodRoutine(@RequestPart String dogId, @RequestPart String routine) {
		return dogDetailsService.saveFoodRoutine(new Long(dogId), routine);
	}
	
	@GetMapping("dog/getTrackedFood")
	public List<String> getTrackedFood(@RequestParam String dogId){
		return dogDetailsService.getTrackedFood(new Long(dogId));
	}
	
	@PostMapping("dog/trackFood")
	public boolean trackFood(@RequestPart String dogId, @RequestPart String data) {
		return dogDetailsService.trackFood(new Long(dogId), data);
	}
	
	@GetMapping("dog/recommendedFood")
	public String recommendFood(@RequestParam String dogId) {
		return dogDetailsService.getRecommendedFood(new Long(dogId));
	}

	@GetMapping("dog/getActivityRoutine")
	public String getActivityRoutine(@RequestParam String dogId) {
		return dogDetailsService.getActivityRoutine(new Long(dogId));
	}
	
	@PostMapping("dog/saveActivityRoutine")
	public boolean saveActivityRoutine(@RequestPart String dogId, @RequestPart String routine) {
		return dogDetailsService.saveActivityRoutine(new Long(dogId), routine);
	}
	
	@GetMapping("dog/getTrackedActivity")
	public List<String> getTrackedActivity(@RequestParam String dogId){
		return dogDetailsService.getTrackedActivity(new Long(dogId));
	}
	
	@PostMapping("dog/trackActivity")
	public boolean trackActivity(@RequestPart String dogId, @RequestPart String data) {
		return dogDetailsService.trackActivity(new Long(dogId), data);
	}
	
	@GetMapping("dog/recommendedActivity")
	public String recommendedActivity(@RequestParam String dogId) {
		return dogDetailsService.getRecommendedActivity(new Long(dogId));
	}

	@GetMapping("dog/getVaccinationRoutine")
	public String getVaccinationRoutine(@RequestParam String dogId) {
		return dogDetailsService.getVaccinationRoutine(new Long(dogId));
	}
	
	@PostMapping("dog/saveVaccinationRoutine")
	public boolean saveVaccinationRoutine(@RequestPart String dogId, @RequestPart String routine) {
		return dogDetailsService.saveVaccinationRoutine(new Long(dogId), routine);
	}
	
	@GetMapping("dog/getTrackedVaccination")
	public List<String> getTrackedVaccination(@RequestParam String dogId){
		return dogDetailsService.getTrackedVaccination(new Long(dogId));
	}
	
	@PostMapping("dog/trackVaccination")
	public boolean trackVaccination(@RequestPart String dogId, @RequestPart String data) {
		return dogDetailsService.trackVaccination(new Long(dogId), data);
	}
	
	@GetMapping("dog/getGroomingRoutine")
	public String getGroomingRoutine(@RequestParam String dogId) {
		return dogDetailsService.getGroomingRoutine(new Long(dogId));
	}
	
	@PostMapping("dog/saveGroomingRoutine")
	public boolean saveGroomingRoutine(@RequestPart String dogId, @RequestPart String routine) {
		return dogDetailsService.saveGroomingRoutine(new Long(dogId), routine);
	}
	
	@GetMapping("dog/getTrackedGrooming")
	public List<String> getTrackedGrooming(@RequestParam String dogId){
		return dogDetailsService.getTrackedGrooming(new Long(dogId));
	}
	
	@PostMapping("dog/trackGrooming")
	public boolean trackGrooming(@RequestPart String dogId, @RequestPart String data) {
		return dogDetailsService.trackGrooming(new Long(dogId), data);
	}
	
	@GetMapping("dog/recommendedGrooming")
	public String recommendedGrooming(@RequestParam String dogId) {
		return dogDetailsService.getRecommendedGrooming(new Long(dogId));
	}
	
	
	@GetMapping("info")
	public String getInfo(@RequestParam String type) {
		return informationService.getInformation(type);
	}
	
}
