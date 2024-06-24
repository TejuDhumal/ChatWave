// Authentication Controller, which is responsible for handling user authentication requests.

package com.axis.team4.codecrafters.user_service.controller;

import com.axis.team4.codecrafters.user_service.config.JwtTokenProvider;
import com.axis.team4.codecrafters.user_service.controller.mapper.UserDtoMapper;
import com.axis.team4.codecrafters.user_service.dto.UserDto;
import com.axis.team4.codecrafters.user_service.exception.UserException;
import com.axis.team4.codecrafters.user_service.modal.User;
import com.axis.team4.codecrafters.user_service.repository.UserRepository;
import com.axis.team4.codecrafters.user_service.request.*;
import com.axis.team4.codecrafters.user_service.response.AuthResponse;
import com.axis.team4.codecrafters.user_service.service.CustomUserDetailsService;
import com.axis.team4.codecrafters.user_service.service.EmailService;
import com.axis.team4.codecrafters.user_service.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private CustomUserDetailsService customUserDetails;

  @Autowired private EmailService emailService;

  @Autowired private UserService userService;

 //This method is used to create a new user.
  
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createUserHandler(
      @Valid @RequestBody User user) throws UserException, MessagingException,
                                            jakarta.mail.MessagingException {
    String email = user.getEmail();
    String password = user.getPassword();
    String full_name = user.getFull_name();
    String username = user.getUsername();

    Optional<User> isEmailExist = userRepository.findByEmail(email);

    if (isEmailExist.isPresent()) {
      throw new UserException("Email Is Already Used With Another Account");
    }

    Optional<User> isUsernameExist = userRepository.findByUsername(username);

    if (isUsernameExist.isPresent()) {
      throw new UserException("Username Is Already Used With Another Account");
    }

    User createdUser = new User();
    createdUser.setEmail(email);
    createdUser.setFull_name(full_name);
    createdUser.setUsername(username);
    createdUser.setPassword(passwordEncoder.encode(password));
    createdUser.setVerify(0); 	    // Set verify to 0 for email verification
    String otp = generateOtp(); 
    createdUser.setOtp(otp); // Generate OTP for email verification
    userRepository.save(createdUser); // Save user to database

    emailService.sendOtp(email, otp); // Send OTP to email

    AuthResponse authResponse = new AuthResponse();
    authResponse.setStatus(true); // Set status to true
    authResponse.setMessage(
        "User registered successfully. Please verify your email."); // Set message

    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }
  
//This method is used to verify the OTP.
  
  @PostMapping("/verifyOtp")
  public ResponseEntity<AuthResponse> verifyOtp(
      @RequestBody OtpRequest otpRequest) {
    Optional<User> userOptional =
        userRepository.findByEmail(otpRequest.getEmail());
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (user.getOtp().equals(otpRequest.getOtp())) {
        user.setVerify(1);
        userRepository.save(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setMessage("OTP verified successfully.");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
      }
    }
    AuthResponse authResponse = new AuthResponse();
    authResponse.setStatus(false);
    authResponse.setMessage("Invalid OTP. Please try again.");
    return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
  }

 //This method is used to sign in the user. 
  
  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> signin(
      @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    Optional<User> userOptional = userRepository.findByEmail(username);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (user.isVerify() == 0) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(false);
        authResponse.setMessage("Please verify your email.");
        return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
      } else if (user.isVerify() == -1) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(false);
        authResponse.setMessage("Your account is deactivated.");
        return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
      }
    }

    Authentication authentication = authenticate(username, password); // Authenticate user
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtTokenProvider.generateJwtToken(authentication); // Generate JWT token
    AuthResponse authResponse = new AuthResponse();
    authResponse.setStatus(true);
    authResponse.setJwt(token);

    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

  private Authentication authenticate(String username, String password) {
    UserDetails userDetails = customUserDetails.loadUserByUsername(username); // Load user by username

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid username or password");
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid username or password");
    }
    return new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
  }
  
//This method is used to get the user details.  

  @PostMapping("/deactivate")
  public ResponseEntity<AuthResponse> deactivateUser(
      @RequestBody OtpRequest otpRequest)
      throws MessagingException, jakarta.mail.MessagingException {
    try {
      userService.deactivateUser(otpRequest.getEmail());
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(true);
      authResponse.setMessage("User deactivated successfully.");
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    } catch (UserException e) {
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(false);
      authResponse.setMessage(e.getMessage());
      return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }
  }

 //This method is used to request the OTP for reactivation. 
  
  @PostMapping("/reactivate")
  public ResponseEntity<AuthResponse> reactivateUser(
      @RequestBody OtpRequest otpRequest) {
    try {
      userService.reactivateUser(otpRequest.getEmail(), otpRequest.getOtp());
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(true);
      authResponse.setMessage("User reactivated successfully.");
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    } catch (UserException e) {
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(false);
      authResponse.setMessage(e.getMessage());
      return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }
  }

//This method is used to request the OTP for reactivation.  
  
  @PostMapping("/requestReactivateOtp")
  public ResponseEntity<AuthResponse> requestReactivateOtp(
      @RequestBody OtpRequest otpRequest)
      throws MessagingException, jakarta.mail.MessagingException {
    Optional<User> userOptional =
        userRepository.findByEmail(otpRequest.getEmail());
    if (!userOptional.isPresent()) {
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(false);
      authResponse.setMessage("User not found");
      return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }
    User user = userOptional.get();
    String otp = generateOtp();
    user.setOtp(otp);
    userRepository.save(user);
    emailService.sendReactivateOtp(user.getEmail(), otp);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setStatus(true);
    authResponse.setMessage("OTP sent to email for reactivation.");
    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }
  
 //This method is used to request the OTP for password reset. 

  @PostMapping("/forgetPassword")
  public ResponseEntity<AuthResponse> forgetPassword(
      @RequestBody OtpRequest otpRequest)
      throws MessagingException, jakarta.mail.MessagingException {
    try {
      userService.forgetPassword(otpRequest.getEmail());
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(true);
      authResponse.setMessage("OTP sent to email.");
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    } catch (UserException e) {
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(false);
      authResponse.setMessage(e.getMessage());
      return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }
  }
  
 //This method is used to reset the password. 

  @PostMapping("/resetPassword")
  public ResponseEntity<AuthResponse> resetPassword(
      @RequestBody ResetPasswordRequest resetPasswordRequest) {
    try {
      userService.resetPassword(resetPasswordRequest.getEmail(),
          resetPasswordRequest.getOtp(), resetPasswordRequest.getNewPassword());
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(true);
      authResponse.setMessage("Password reset successfully.");
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    } catch (UserException e) {
      AuthResponse authResponse = new AuthResponse();
      authResponse.setStatus(false);
      authResponse.setMessage(e.getMessage());
      return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
    }
  }

//This method is used to get the user details.  
  
  @PutMapping("/update/{userId}")
  public ResponseEntity<UserDto> updateUserHandler(
      @RequestBody UpdateUserRequest req, @PathVariable Integer userId)
      throws UserException {
    User updatedUser = userService.updateUser(userId, req);
    UserDto userDto = UserDtoMapper.toUserDTO(updatedUser);

    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  private String generateOtp() {
    return String.valueOf(
        (int) (Math.random() * 900000) + 100000); // Generate a 6-digit OTP
  }
}
