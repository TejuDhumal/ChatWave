package com.axis.userservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axis.userservice.entity.Account;
import com.axis.userservice.entity.User;
import com.axis.userservice.entity.dto.Event;
import com.axis.userservice.entity.dto.EventUser;
import com.axis.userservice.entity.dto.Requests;
import com.axis.userservice.entity.dto.UserDTO;
import com.axis.userservice.service.AccountService;
import com.axis.userservice.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public AccountService accountService;
    
    Event event = new Event();
    EventUser eventUser = new EventUser();

    @GetMapping("/")
    public ResponseEntity findAllUsers(){
        event.setSubject("Data retreive successfully");
        event.setResult(userService.findAll());
        return ResponseEntity.ok(event);
    }

    @PostMapping("/")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
    	
        // Create and save User without the password
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setMobile_no(userDTO.getMobile_no());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());
        user.setIsActive(true);

        User savedUser = userService.save(user);
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setEmail(user.getEmail());
        account.setPassword(user.getPassword());
        accountService.save(account);
        
        eventUser.setSubject("Data added successfully");
        eventUser.setResult(savedUser);

        return ResponseEntity.created(new URI("/user/" + savedUser.getUserId())).body(eventUser);
    }


   
    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable int userId) throws URISyntaxException {
        // Create and save User without the password
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setMobile_no(userDTO.getMobile_no());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());
       
        User savedUser = userService.save(user);
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setEmail(user.getEmail());
        account.setPassword(user.getPassword());
        accountService.save(account);

        eventUser.setSubject("Data updated successfully");
        eventUser.setResult(savedUser);
        return ResponseEntity.created(new URI("/user/" + savedUser.getUserId())).body(eventUser);
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId) {
        User result = userService.findById(userId);
        accountService.deleteById(result.getUserId());
        userService.deleteById(userId);
        return ResponseEntity.ok("deleted");
    }
    
    @GetMapping("/deactivate/{userId}")
    public ResponseEntity dactivateUser(@PathVariable int userId) {
        User result = userService.findById(userId);
        result.setIsActive(false);
        eventUser.setSubject("Account deactivate successfully");
        eventUser.setResult(result);
        return ResponseEntity.ok(eventUser);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
         eventUser.setSubject("Data retreive successfully");
        eventUser.setResult(userService.findByUsername(username));
        return ResponseEntity.ok(eventUser);
    }



    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable int userId) {
        User result = userService.findById(userId);
        eventUser.setSubject("Data retreive successfully");
        eventUser.setResult(result);
        return ResponseEntity.ok(eventUser);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email) {
        userService.registerUser(email);
        return ResponseEntity.ok("Verification email sent!");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        boolean isVerified = userService.verifyUser(token);
        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
    }
    
  
}
