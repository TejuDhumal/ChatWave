package com.axis.userservice.service.impl;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.axis.userservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axis.userservice.entity.Account;
import com.axis.userservice.entity.User;
import com.axis.userservice.entity.dto.Requests;
import com.axis.userservice.repo.AccountRepository;
import com.axis.userservice.repo.UserRepository;
import com.axis.userservice.service.UserService;


@Service	
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private EmailService emailService;
	

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
	public User findById(int userId) {
		// TODO Auto-generated method stub
		return userRepository.findByuserId(userId);
	}

	@Override
	public void deleteById(int userId) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(userId);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}


	public User registerUser(String email) {
		User user = new User();
		user.setEmail(email);
		userRepository.save(user);

		emailService.sendVerificationEmail(email, user.getVerificationToken());
		return user;
	}

	public boolean verifyUser(String token) {
		Optional<User> userOptional = userRepository.findByVerificationToken(token);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setVerifyStatus(true);
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	 

	  

}
