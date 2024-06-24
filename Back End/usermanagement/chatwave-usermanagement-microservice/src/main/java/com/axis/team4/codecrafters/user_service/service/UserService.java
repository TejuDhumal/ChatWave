// This is the service interface for the user service. It contains the methods that are implemented in the UserServiceImpl class. The methods are used to find the user profile, update the user profile, find the user by id, search the user, deactivate the user, reactivate the user, forget the password, and reset the password. The methods throw UserException and MessagingException.

package com.axis.team4.codecrafters.user_service.service;

import java.util.List;

import javax.mail.MessagingException;

import com.axis.team4.codecrafters.user_service.exception.UserException;
import com.axis.team4.codecrafters.user_service.modal.User;
import com.axis.team4.codecrafters.user_service.request.UpdateUserRequest;

public interface UserService {

    User findUserProfile(String jwt) throws UserException;
    
    User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
    
    User findUserById(Integer userId) throws UserException;
    
    List<User> searchUser(String query);
    
    void deactivateUser(String email) throws UserException, MessagingException, jakarta.mail.MessagingException;
    
    void reactivateUser(String email, String otp) throws UserException;
    
    void forgetPassword(String email) throws UserException, MessagingException, jakarta.mail.MessagingException;
    
    void resetPassword(String email, String otp, String newPassword) throws UserException;
}
