package com.axis.userservice.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.userservice.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByuserId(int userId);
	
   User findByUsername(String username);

   Optional<User> findByVerificationToken(String token);
   
    
    

}
