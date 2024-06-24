// Purpose: UserDtoMapper class to map User to UserDto.

package com.axis.team4.codecrafters.message_service.controller.mapper;

import java.util.HashSet;
import java.util.Set;

import com.axis.team4.codecrafters.message_service.dto.UserDto;
import com.axis.team4.codecrafters.message_service.modal.User;

public class UserDtoMapper {

	
	public static UserDto toUserDTO(User user) {
		
		UserDto userDto=new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setFull_name(user.getFull_name());
		userDto.setId(user.getId());
		userDto.setProfile_pic(user.getProfile_picture());
		userDto.setUsername(user.getUsername());
		userDto.setBio(user.getBio());
		
		return userDto;
		
	}
	
	public static HashSet<UserDto> toUserDtos(Set<User> set){
		HashSet<UserDto> userDtos=new HashSet<>();
		
		for(User user:set) {
			UserDto userDto=toUserDTO(user);
			userDtos.add(userDto);
		}
		
		return userDtos;
	}
}