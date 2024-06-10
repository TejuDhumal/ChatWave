package com.axis.ChatWave.controllers;

import com.axis.ChatWave.dtos.ResetPasswordRequest;
import com.axis.ChatWave.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController {

    @Autowired
    private AuthService authService;

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        boolean isPasswordReset = authService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        if (isPasswordReset) {
            return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token or token expired", HttpStatus.BAD_REQUEST);
        }
    }
}


