package com.axis.userservice.service;

public interface EmailService {

    public void sendVerificationEmail(String toEmail, String token);
}
