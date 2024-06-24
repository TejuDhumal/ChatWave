// User Repository class

package com.axis.team4.codecrafters.user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axis.team4.codecrafters.user_service.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:uname%")
	List<User> searchUsers(@Param("uname") String uname);
	


}
