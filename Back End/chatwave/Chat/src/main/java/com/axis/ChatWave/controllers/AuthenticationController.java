package com.axis.ChatWave.controllers;

import com.axis.ChatWave.dtos.AuthenticationRequest;
import com.axis.ChatWave.dtos.AuthenticationResponse;
import com.axis.ChatWave.services.jwt.UserDetailsServiceImpl;
import com.axis.ChatWave.utils.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager ;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/authentication")
	public AuthenticationResponse createAuthenticationToken(@RequestBody 
			AuthenticationRequest authenticationRequest, HttpServletResponse response )throws
	BadCredentialsException, DisabledException, UsernameNotFoundException ,IOException, java.io.IOException{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		}catch(BadCredentialsException e){
			throw new BadCredentialsException("Either Username or Password is incorrect");
		}catch(DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not registered.");
			return null;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(
				authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		return new AuthenticationResponse(jwt);
	}

}
