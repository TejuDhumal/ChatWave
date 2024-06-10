//package com.axis.ChatWave.services;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    @Value("${spring.mail.username}")
//    private String fromEmailId;
//
//    public boolean sendPasswordResetEmail(String recipient, String token) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(recipient);
//            message.setFrom(fromEmailId);
//            message.setSubject("Password Reset Request");
//            message.setText("To reset your password, click the link below:\n" +
//                            "http://localhost:8080/reset-password?token=" + token);
//            emailSender.send(message);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}

package com.axis.ChatWave.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmailId;

    public boolean sendPasswordResetEmail(String recipient, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setFrom(fromEmailId);
            message.setSubject("Password Reset Request");
            message.setText(token);
            emailSender.send(message);
            LOGGER.log(Level.INFO, "Password reset email sent successfully to: {0}", recipient);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error sending password reset email to: " + recipient, e);
            return false;
        }
    }
}





