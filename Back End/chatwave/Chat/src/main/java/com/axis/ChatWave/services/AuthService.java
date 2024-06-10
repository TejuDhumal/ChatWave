package com.axis.ChatWave.services;

import com.axis.ChatWave.dtos.SignupRequest;
import com.axis.ChatWave.dtos.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupRequest signupRequest);

    boolean sendPasswordResetToken(String email);

    boolean resetPassword(String token, String newPassword);
}


