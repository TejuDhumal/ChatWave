// Purpose: Implementation of UserDetailsService interface to load user-specific data.

package com.axis.team4.codecrafters.message_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axis.team4.codecrafters.message_service.modal.User;
import com.axis.team4.codecrafters.message_service.repository.jpa.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		
		   this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		        Optional<User> user = userRepository.findByEmail(username);
		        
		        if (user.isEmpty()) {
		            throw new UsernameNotFoundException("User not found with username: " + username);
		        }
		        List<GrantedAuthority> authorities = new ArrayList<>();
		        
		        
		        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
		    }
		

	}


