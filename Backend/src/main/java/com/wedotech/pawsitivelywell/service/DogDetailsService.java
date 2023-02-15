package com.wedotech.pawsitivelywell.service;

import org.springframework.stereotype.Service;

import com.wedotech.pawsitivelywell.model.DogDetails;

@Service
public interface DogDetailsService {

	DogDetails[] getDogsByUser(String emailId);

}
