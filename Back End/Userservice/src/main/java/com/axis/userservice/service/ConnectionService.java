package com.axis.userservice.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.axis.userservice.entity.Connection;
import com.axis.userservice.entity.dto.Requests;

public interface ConnectionService {

	void sendRequest(String username, Requests requests, int user_id);

	Boolean ActionRequest(int user_id, Requests requests, String username);

	Connection getConnectionById(int user_id);

}
