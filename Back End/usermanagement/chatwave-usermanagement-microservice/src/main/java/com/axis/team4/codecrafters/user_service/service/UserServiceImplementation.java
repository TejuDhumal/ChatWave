// This is the UserServiceImplementation.java file. This file contains the implementation of the UserService interface.

package com.axis.team4.codecrafters.user_service.service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;


import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.user_service.exception.UserException;
import com.axis.team4.codecrafters.user_service.modal.User;
import com.axis.team4.codecrafters.user_service.repository.UserRepository;
import com.axis.team4.codecrafters.user_service.request.UpdateUserRequest;
import com.axis.team4.codecrafters.user_service.config.JwtTokenProvider;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // Find user profile

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = jwtTokenProvider.getEmailFromToken(jwt);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UserException("User not found");
        }
        return optionalUser.get();
    }

    // Update user profile
    
    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserException("User not found");
        }

        User user = optionalUser.get();

        if (req.getFull_name() != null) {
            user.setFull_name(req.getFull_name());
        }
        if (req.getProfile_picture() != null) {
            user.setProfile_picture(req.getProfile_picture());
        }
        if (req.getBio() != null) {
            user.setBio(req.getBio());
        }

        userRepository.save(user);
        return user;
    }
    
    // Find user by id

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserException("User not found");
        }
        return optionalUser.get();
    }

    @Override
    public List<User> searchUser(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        List<User> users = userRepository.searchUsers(query);
        return users != null ? users : Collections.emptyList();
    }

    // Deactivate user

    @Override
    public void deactivateUser(String email) throws UserException, MessagingException, jakarta.mail.MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserException("User not found");
        }
        User user = userOptional.get();
        user.setVerify(-1);
        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);
        emailService.sendReactivateOtp(email, otp);
    }
    
    // Reactivate user

    @Override
    public void reactivateUser(String email, String otp) throws UserException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserException("User not found");
        }
        User user = userOptional.get();
        if (!user.getOtp().equals(otp)) {
            throw new UserException("Invalid OTP");
        }
        user.setVerify(1);
        userRepository.save(user);
    }
    
    //Forget password

    @Override
    public void forgetPassword(String email) throws UserException, MessagingException, jakarta.mail.MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserException("User not found");
        }
        User user = userOptional.get();
        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);
        emailService.sendResetPasswordOtp(email, otp);
    }

    // Reset password
    
    @Override
    public void resetPassword(String email, String otp, String newPassword) throws UserException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserException("User not found");
        }
        User user = userOptional.get();
        if (!user.getOtp().equals(otp)) {
            throw new UserException("Invalid OTP");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    // Generate OTP

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Generate a 6-digit OTP
    }
}
