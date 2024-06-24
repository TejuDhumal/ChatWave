// Purpose: Service interface for User entity. It contains methods to find user profile, update user profile, find user by id and search user.

package com.axis.team4.codecrafters.message_service.service;

import java.util.List;

import com.axis.team4.codecrafters.message_service.dto.UserDto;
import com.axis.team4.codecrafters.message_service.exception.UserException;
import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public User findUserById(Integer userId) throws UserException;
	
	public List<User> searchUser(String query);
}
