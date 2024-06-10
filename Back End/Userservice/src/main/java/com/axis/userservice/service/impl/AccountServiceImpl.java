package com.axis.userservice.service.impl;



import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axis.userservice.entity.Account;
import com.axis.userservice.entity.User;
import com.axis.userservice.repo.AccountRepository;
import com.axis.userservice.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    public AccountRepository accountRepository;

//	@Autowired
//    private JavaMailSender javaMailSender;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public Account save(Account account) {
        Account account1 = accountRepository.save(account);
        return account1;
    }



	@Override
	public void deleteById(int userId) {
		// TODO Auto-generated method stub
		 accountRepository.deleteById(userId);
		
	}
	
//	  public void sendResetToken(String email) {
//	        Account account = accountRepository.findByEmail(email);
//	        if (account != null) {
//	            String token = UUID.randomUUID().toString();
//	            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
//	            account.setResetToken(token);
//	            account.setTokenExpiration(expiryDate);
//	            accountRepository.save(account);
//
//	            String resetUrl = "http://localhost:8080/api/reset-password?token=" + token;
//	            sendEmail(account.getEmail(), "Password Reset Request", resetUrl);
//	        }
//	    }
//
//	    public void resetPassword(String token, String newPassword) {
//	        Account account = accountRepository.findByResetToken(token);
//	        if (account != null && account.getTokenExpiration().isAfter(LocalDateTime.now())) {
//	        	account.setPassword(newPassword);
//	        	account.setResetToken(null);
//	        	account.setTokenExpiration(null);
//	        	accountRepository.save(account);
//	        }
//	    }
//
//	    private void sendEmail(String to, String subject, String text) {
//	    	try {
//	            SimpleMailMessage message = new SimpleMailMessage();
//	            message.setTo(to);
//	            message.setSubject(subject);
//	            message.setText(text);
//	            javaMailSender.send(message);
//	        } catch (MailException e) {
//	            // Log the exception
//	            e.printStackTrace();
//	        }
	    }
	    
	    
	 


