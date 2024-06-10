package com.axis.userservice.service.impl;




import com.axis.userservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendVerificationEmail(String toEmail, String token) {

		String subject = "Email Verification";
			String verificationUrl = "http://localhost:8085/api/user/verify-email?token=" + token;
			String body = "Please click the following link to verify your email: " + verificationUrl;

		System.out.println("email");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toEmail);
			message.setSubject(subject);
			message.setText(body);

			mailSender.send(message);
		System.out.println("emial sent");
		}
}
