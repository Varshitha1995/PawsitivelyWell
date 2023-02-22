package com.wedotech.pawsitivelywell.service;

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
	public boolean updateUser(String emailId, JsonObject userDetails) {
		UserDetails user = userRepository.getUser(emailId);
		if (user == null)
			return false;
		user.setFirstName(userDetails.get("firstName").getAsString());
		user.setLastName(userDetails.get("lastName").getAsString());
		user.setPassword(userDetails.get("password").getAsString());
		userRepository.save(user);
		return true;
	}

	@Override
	public Set<DogDetails> getDogsByEmail(String emailId) {
		Long uId = userRepository.getUserId(emailId);
		Set<Long> dogIds = userRepository.getDogIds(uId);
		Set<DogDetails> dogDetails = userRepository.getDogsByIds(dogIds);
		return dogDetails;
	}

}
