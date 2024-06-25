// Used for mapping User to UserDto

package com.axis.team4.codecrafters.user_service.controller.mapper;

import com.axis.team4.codecrafters.user_service.dto.UserDto;
import com.axis.team4.codecrafters.user_service.modal.User;
import java.util.HashSet;
import java.util.Set;

public class UserDtoMapper {
  public static UserDto toUserDTO(User user) {
    UserDto userDto = new UserDto();

    userDto.setEmail(user.getEmail());
    userDto.setFull_name(user.getFull_name());
    userDto.setUsername(user.getUsername());
    userDto.setId(user.getId());
    userDto.setProfile_pic(user.getProfile_picture());
    userDto.setBio(user.getBio());

    return userDto;
  }

 // Used for mapping Set<User> to Set<UserDto> 
  
  public static HashSet<UserDto> toUserDtos(Set<User> set) {
    HashSet<UserDto> userDtos = new HashSet<>();

    for (User user : set) {
      UserDto userDto = toUserDTO(user);
      userDtos.add(userDto);
    }

    return userDtos;
  }
}
