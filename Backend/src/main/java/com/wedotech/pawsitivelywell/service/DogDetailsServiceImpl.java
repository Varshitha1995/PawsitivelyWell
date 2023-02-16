package com.wedotech.pawsitivelywell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.repository.DogRepository;
import com.wedotech.pawsitivelywell.repository.UserRepository;

@Service
public class DogDetailsServiceImpl implements DogDetailsService{

	@Autowired
	DogRepository dogRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public DogDetails[] getDogsByUser(String emailId) {
//		Long userId = userRepository.getUserId(emailId);
//		DogDetails[] dogDetails = dogRepository.getDogsByUser(userId);
		DogDetails[] dogDetails = dogRepository.getDogsByEmail(emailId);
		return dogDetails;
		
	}
	
}
