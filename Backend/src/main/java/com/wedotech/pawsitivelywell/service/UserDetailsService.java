package com.wedotech.pawsitivelywell.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;

@Service
public interface UserDetailsService {

	boolean validateLogin(String emailId, String password);

	boolean createUser(String firstName, String lastName, String emailId, String password);

	boolean updateUser(String emailId, JsonObject userDetails);

	Set<DogDetails> getDogsByEmail(String emailId);

}
