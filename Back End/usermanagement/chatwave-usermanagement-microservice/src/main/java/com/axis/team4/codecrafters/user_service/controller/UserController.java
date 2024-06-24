// User Controller class for handling user requests

package com.axis.team4.codecrafters.user_service.controller;

import com.axis.team4.codecrafters.user_service.controller.mapper.UserDtoMapper;
import com.axis.team4.codecrafters.user_service.dto.UserDto;
import com.axis.team4.codecrafters.user_service.exception.UserException;
import com.axis.team4.codecrafters.user_service.modal.User;
import com.axis.team4.codecrafters.user_service.request.UpdateUserRequest;
import com.axis.team4.codecrafters.user_service.service.UserService;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired private UserService userService;
  
 // Update user request handler 

  @PutMapping("/update/{userId}")
  public ResponseEntity<UserDto> updateUserHandler(
      @RequestBody UpdateUserRequest req, @PathVariable Integer userId)
      throws UserException {
    System.out.println("updated user -------- ");
    User updatedUser = userService.updateUser(userId, req);
    UserDto userDto = UserDtoMapper.toUserDTO(updatedUser);

    return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
  }

// Profile request handler  
  
  @GetMapping("/profile")
  public ResponseEntity<UserDto> getUserProfileHandler(
      @RequestHeader("Authorization") String jwt) throws UserException {
    System.out.println("req profile ----- ");

    User user = userService.findUserProfile(jwt);

    UserDto userDto = UserDtoMapper.toUserDTO(user);

    System.out.println("req profile " + userDto.getEmail());

    return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
  }
  
// Search user request handler  

  @GetMapping("/search")
  public ResponseEntity<HashSet<UserDto>> searchUsersByName(
      @RequestParam("name") String name) {
    System.out.println("search user ------- ");

    List<User> users = userService.searchUser(name);

    HashSet<User> set = new HashSet<>(users);

    HashSet<UserDto> userDtos = UserDtoMapper.toUserDtos(set);

    System.out.println("search result ------ " + userDtos);

    return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
  }
}
