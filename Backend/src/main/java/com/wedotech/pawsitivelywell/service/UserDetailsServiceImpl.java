package com.wedotech.pawsitivelywell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wedotech.pawsitivelywell.model.UserDetails;
import com.wedotech.pawsitivelywell.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean validateLogin(String emailId, String password) {
		UserDetails user = userRepository.getUser(emailId);
		if(user==null)
			return false;
		String storedPassword = user.getPassword();
		if(password.equals(storedPassword))
			return true;
		return false;
		
	}
	
	@Override
	public boolean createUser(String firstName, String lastName, String emailId, String password) {
		UserDetails user = userRepository.getUser(emailId);
		if(user!=null)
			return false;
		user = new UserDetails(firstName, lastName, emailId, password);
		userRepository.save(user);
		return true;
	}

}
