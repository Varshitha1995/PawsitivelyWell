package com.wedotech.pawsitivelywell.service;

import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {

	boolean validateLogin(String emailId, String password);

}
