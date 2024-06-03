package com.axis.chatwave.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.chatwave.entity.Account;
import com.axis.chatwave.entity.User;
import com.axis.chatwave.entity.dto.UserDTO;
import com.axis.chatwave.service.AccountService;
import com.axis.chatwave.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public AccountService accountService;

    @GetMapping("/")
    public ResponseEntity findAllUsers(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
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

        User savedUser = userService.save(user);
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setEmail(user.getEmail());
        account.setPassword(user.getPassword());
        accountService.save(account);

        return ResponseEntity.created(new URI("/user/" + savedUser.getUserId())).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        User result = userService.findById(id);
        return ResponseEntity.ok(result);
    }



    @DeleteMapping("/")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User result = userService.findById(id);
        userService.deleteById(id);
        return ResponseEntity.ok(result);
    }
}
