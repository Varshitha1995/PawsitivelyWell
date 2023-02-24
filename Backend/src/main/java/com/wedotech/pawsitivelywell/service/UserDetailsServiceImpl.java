package com.wedotech.pawsitivelywell.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.repository.DogRepository;
import com.wedotech.pawsitivelywell.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	DogRepository dogRepository;

	@Override
	public boolean validateLogin(String emailId, String password) {
		UserDetails user = userRepository.getUser(emailId);
		if (user == null)
			return false;
		String storedPassword = user.getPassword();
		if (password.equals(storedPassword))
			return true;
		return false;

	}

	@Override
	public boolean createUser(String firstName, String lastName, String emailId, String password) {
		UserDetails user = userRepository.getUser(emailId);
		if (user != null)
			return false;
		user = new UserDetails(firstName, lastName, emailId, password);
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean updateUser(String emailId, String firstName, String lastName, String password) {
		UserDetails user = userRepository.getUser(emailId);
		if (user == null)
			return false;
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		userRepository.save(user);
		return true;
	}

	@Override
	public UserDetails getUserDetails(String emailId) {
		UserDetails user = userRepository.getUser(emailId);
		if (user == null)
			return null;
		return user;
	}
	
	@Override
	public Set<DogDetails> getDogsByEmail(String emailId) {
		Long uId = userRepository.getUserId(emailId);
		Set<Long> dogIds = userRepository.getDogIds(uId);
		Set<DogDetails> dogDetails = userRepository.getDogsByIds(dogIds);
		return dogDetails;
	}

}
