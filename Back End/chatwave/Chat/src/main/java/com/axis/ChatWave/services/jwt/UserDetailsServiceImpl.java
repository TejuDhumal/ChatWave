package com.axis.ChatWave.services.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axis.ChatWave.models.User;
import com.axis.ChatWave.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{ 
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public  UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	    
		User user = userRepository.findFirstByEmail(email);
		if(user == null ) {
			throw new UsernameNotFoundException("User not Found with email" + email); 
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
				user.getPassword(), new ArrayList<>());
		
	}

}
