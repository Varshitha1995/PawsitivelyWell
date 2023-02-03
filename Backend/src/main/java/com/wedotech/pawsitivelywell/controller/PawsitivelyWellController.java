package com.wedotech.pawsitivelywell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wedotech.pawsitivelywell.service.UserDetailsService;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
public class PawsitivelyWellController {
	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping("user/login")
	public boolean validateLogin(@RequestParam String emailId, @RequestParam String password) {
		return userDetailsService.validateLogin(emailId, password);
	}

}
