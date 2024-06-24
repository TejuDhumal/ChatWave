// Purpose: Implementation of UserService interface. It is used to provide the implementation of the methods declared in the UserService interface.

package com.axis.team4.codecrafters.message_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.message_service.config.JwtTokenProvider;
import com.axis.team4.codecrafters.message_service.dto.UserDto;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.repository.jpa.UserRepository;
import com.axis.team4.codecrafters.message_service.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	//This method is used to register a new user.

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		
		System.out.println("update find user ------- ");
		User user=findUserById(userId);
		
		System.out.println("update find user ------- "+user);
		
		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		
		return userRepo.save(user);
	}
	
	//This method is used to update the user profile.

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt=userRepo.findById(userId);
		
		if(opt.isPresent()) {
			User user=opt.get();
			
			return user;
		}
		throw new UserException("user not exist with id "+userId);
	}
	
	//This method is used to find the user by its id.

	@Override
	public User findUserProfile(String jwt) {
		String email = jwtTokenProvider.getEmailFromToken(jwt);
		
		Optional<User> opt=userRepo.findByEmail(email);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new BadCredentialsException("recive invalid token");
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepo.searchUsers(query);
		
	}
	
	

}
