package com.wedotech.pawsitivelywell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.service.DogDetailsService;
import com.wedotech.pawsitivelywell.service.UserDetailsService;

@CrossOrigin(origins="*", allowedHeaders="*")
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
	public boolean createUser(@RequestPart String firstName, @RequestPart String lastName, @RequestPart String emailId, @RequestPart String password) {
		return userDetailsService.createUser(firstName, lastName, emailId, password);
	}
	
	@GetMapping("user/getdogs")
	public DogDetails[] getDogsByUser(@RequestParam String emailId) {
		return dogDetailsService.getDogsByUser(emailId);
	}

}
