package com.axis.userservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.userservice.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

	
	
	public Connection findByUserId(int userId);

}
