package com.axis.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.axis.userservice.entity.Connection;
import com.axis.userservice.entity.User;
import com.axis.userservice.entity.dto.Requests;
import com.axis.userservice.repo.ConnectionRepository;
import com.axis.userservice.repo.UserRepository;
import com.axis.userservice.service.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ConnectionRepository connectionRepository;
	
	List<Requests> list = new ArrayList<>();
	
	
	@Override
	public void sendRequest(String username, Requests requests, int userId) {
	    // Find the existing user by username
	    User user = userRepository.findByUsername(username);
	    User user1 = userRepository.findByuserId(userId);

	    if (user != null && user1 != null) {
	        // Create a new connection for sending request
	        Connection sendConnection = new Connection();
	        sendConnection.setUserId(user.getUserId());
	        
	        // Initialize new list for sending requests
	        List<Requests> sendList = new ArrayList<>();
	        
	        // Set request details for sending user
	        Requests sendRequest = new Requests();
	        sendRequest.setUser_id(user1.getUserId());
	        sendRequest.setUsername(user1.getUsername());
	        
	        // Add the request to the send list and set it in the connection
	        sendList.add(sendRequest);
	        sendConnection.setConnection_send_request(sendList);
	        
	        // Save the updated send connection back to the database
	        connectionRepository.save(sendConnection);

	        // Create a new connection for receiving request
	        Connection receivedConnection = new Connection();
	        receivedConnection.setUserId(user1.getUserId());
	        
	        // Initialize new list for receiving requests
	        List<Requests> receiveList = new ArrayList<>();
	        
	        // Set request details for receiving user
	        Requests receiveRequest = new Requests();
	        receiveRequest.setUser_id(user.getUserId());
	        receiveRequest.setUsername(user.getUsername());
	        
	        // Add the request to the receive list and set it in the connection
	        receiveList.add(receiveRequest);
	        receivedConnection.setConnection_received_request(receiveList);
	        
	        // Save the updated received connection back to the database
	        connectionRepository.save(receivedConnection);
	    } else {
	        throw new RuntimeException("User not found with username: " + username);
	    }
	}

//	   

	@Override
	public Boolean ActionRequest(int userId, Requests requests, String username) {
//		
		Connection connection = connectionRepository.findByUserId(userId);
	    if (!connection.getConnection_received_request().isEmpty()) {
	        // Check if the user has the specific request
	        List<Requests> userRequests = connection.getConnection_received_request();
	        boolean requestUpdated = false;
	        
	        
	        for (int i = 0; i < userRequests.size(); i++) {
	            Requests req = userRequests.get(i);
	           
	            if (req.getUsername().equalsIgnoreCase(username)) {
	                // Update the isConfirm field to true
	            	req.setIsConfirm(requests.getIsConfirm());
	                requestUpdated = requests.getIsConfirm();
	                
	                break;
	            }
	        }
	        
	         if (requestUpdated == true || requestUpdated == false) {
	        	
	            // Save the updated user back to the database
	           connectionRepository.save(connection);
	            return true;
	        } else {
	            return false;
	            
	        }
	    } else {
        	
	        return false;
	    }
////		
//		
	
		
		  
	    }
//	

	@Override
	public Connection getConnectionById(int userId) {
		// TODO Auto-generated method stub
		return connectionRepository.findByUserId(userId);
	}

}
