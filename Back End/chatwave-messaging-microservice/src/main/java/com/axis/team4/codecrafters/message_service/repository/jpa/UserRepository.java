// Purpose: Contains the UserRepository interface. This interface extends JpaRepository and provides methods to interact with the User table in the database. It contains a method to search for users by name.

package com.axis.team4.codecrafters.message_service.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.axis.team4.codecrafters.message_service.modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.full_name LIKE %:name%")
	List<User> searchUsers(@Param("name") String name);


}
