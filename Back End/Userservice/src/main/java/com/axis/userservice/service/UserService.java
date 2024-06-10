package com.axis.userservice.service;

import java.util.List;
import java.util.Optional;

import com.axis.userservice.entity.User;
import com.axis.userservice.entity.dto.Requests;



public interface UserService {

	 List<User> findAll();
	
	 User save(User user);
	
	 User findById(int userId);
	
	 void deleteById(int userId);
	
	 User updateUser(User user);
	
	 User findByUsername(String username);
	public User registerUser(String email);
	public boolean verifyUser(String token);

	 
	
	 
	    
}
