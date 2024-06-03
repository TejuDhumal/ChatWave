package com.axis.chatwave.service;

import java.util.List;

import com.axis.chatwave.entity.User;

public interface UserService {

	 List<User> findAll();
	
	 User save(User user);
	
	 User findById(int id);
	
	 void deleteById(int id);
	
	 User updateUser(User user);
	
}
