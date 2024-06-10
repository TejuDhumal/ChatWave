package com.axis.ChatWave.controllers;

import com.axis.ChatWave.services.AuthService;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgotPasswordController {

    @Autowired
    private AuthService authService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        boolean result = authService.sendPasswordResetToken(email);
        if (result) {
            return ResponseEntity.ok("Password reset email sent successfully");
        } else {
            return ResponseEntity.status(400).body("Error sending password reset email");
        }
    }
}














//package com.axis.ChatWave.controllers;
//
//import com.axis.ChatWave.services.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ForgotPasswordController {
//    
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
//        boolean isEmailSent = authService.sendPasswordResetToken(email);
//        if (isEmailSent) {
//            return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Error sending password reset email", HttpStatus.BAD_REQUEST);
//        }
//    }
//}





