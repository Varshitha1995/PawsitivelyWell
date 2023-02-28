package com.wedotech.pawsitivelywell.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.service.DogDetailsService;
import com.wedotech.pawsitivelywell.service.UserDetailsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PawsitivelyWellController {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	DogDetailsService dogDetailsService;

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

	@PostMapping("user/updateUser")
	public boolean updateUser(@RequestBody String user) {
		JsonObject userDetails = new Gson().fromJson(user, JsonObject.class);
		return userDetailsService.updateUser(userDetails.get("emailId").getAsString(), userDetails);
	}
	
	@PostMapping("dog/createDog")
	public boolean createDog(@RequestPart String dogName,@RequestPart int age,@RequestPart String breed,@RequestPart float weight,@RequestPart String emailId) {
		return dogDetailsService.createDog(dogName, age, breed, weight, emailId);
	}
	
	@PostMapping("dog/addDog")
	public boolean addDog(@RequestPart Long dogId, @RequestPart String emailId) {
		return dogDetailsService.addDog(dogId, emailId);
	}
	
	@PostMapping("dog/updateDog")
	public boolean updateDog(@RequestPart Long dogId, @RequestPart String dogName, @RequestPart int age, @RequestPart String breed, @RequestPart float weight) {
		return dogDetailsService.updateDog(dogId, dogName, age, breed, weight);
	}
	
	@PostMapping("dog/uploadPhoto")
	public boolean uploadPhoto(@RequestPart Long dogId, @RequestPart MultipartFile photo) {
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
		return dogDetailsService.updateDogPhoto(dogId, imageInByte);
		
	}

}
