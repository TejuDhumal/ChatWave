package com.axis.chatwave.service.impl;



import java.util.List;

import com.axis.chatwave.repository.UserRepository;
import com.axis.chatwave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axis.chatwave.entity.User;

@Service	
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;


	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepository.findAll();
        return users;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		User user1 = this.userRepository.save(user);
		return user1;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return userRepository.getById(id);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(id);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

}
