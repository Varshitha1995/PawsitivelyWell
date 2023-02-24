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
import com.wedotech.pawsitivelywell.model.UserDetails;
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

}
