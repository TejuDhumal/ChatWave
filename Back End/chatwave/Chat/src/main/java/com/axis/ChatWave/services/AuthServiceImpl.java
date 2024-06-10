package com.axis.ChatWave.services;

import com.axis.ChatWave.dtos.SignupRequest;
import com.axis.ChatWave.dtos.UserDTO;
import com.axis.ChatWave.models.User;
import com.axis.ChatWave.repositories.UserRepository;
import com.axis.ChatWave.utils.JwtUtil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setPhone(createdUser.getPhone());

        return userDTO;
    }

    public User authenticateUser(String token) {
        String email = jwtUtil.extractEmail(token);
        return userRepository.findFirstByEmail(email);
    }
    
    @Override
    public boolean sendPasswordResetToken(String email) {
        LOGGER.log(Level.INFO, "Attempting to send password reset token to email: {0}", email);
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            LOGGER.log(Level.WARNING, "User not found for email: {0}", email);
            return false;
        }
        String token = jwtUtil.generateToken(user.getEmail());
        boolean isEmailSent = emailService.sendPasswordResetEmail(user.getEmail(), token);
        if (isEmailSent) {
            LOGGER.log(Level.INFO, "Password reset email sent successfully to: {0}", email);
        } else {
            LOGGER.log(Level.SEVERE, "Failed to send password reset email to: {0}", email);
        }
        return isEmailSent;
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findFirstByEmail(email);
        if (user == null || !jwtUtil.validateToken(token, new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()))) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUpdatedBy(currentUser);
        userRepository.save(user);
        return true;
    }
}



//package com.axis.ChatWave.services;
//
//import com.axis.ChatWave.dtos.SignupRequest;
//import com.axis.ChatWave.dtos.UserDTO;
//import com.axis.ChatWave.models.User;
//import com.axis.ChatWave.repositories.UserRepository;
//import com.axis.ChatWave.utils.JwtUtil;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Override
//    public UserDTO createUser(SignupRequest signupRequest) {
//        User user = new User();
//        user.setEmail(signupRequest.getEmail());
//        user.setName(signupRequest.getName());
//        user.setPhone(signupRequest.getPhone());
//        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
//
//        User createdUser = userRepository.save(user);
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail(createdUser.getEmail());
//        userDTO.setName(createdUser.getName());
//        userDTO.setPhone(createdUser.getPhone());
//
//        return userDTO;
//    }
//
//    @Override
//    public boolean sendPasswordResetToken(String email) {
//        User user = userRepository.findFirstByEmail(email);
//        if (user == null) {
//            return false;
//        }
//        String token = jwtUtil.generateToken(user.getEmail());
//        return emailService.sendPasswordResetEmail(user.getEmail(), token);
//    }
//
//    @Override
//    public boolean resetPassword(String token, String newPassword) {
//        String email = jwtUtil.extractUsername(token);
//        User user = userRepository.findFirstByEmail(email);
//        if (user == null || !jwtUtil.validateToken(token, new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()))) {
//            return false;
//        }
//        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
//        userRepository.save(user);
//        return true;
//    }
//}

