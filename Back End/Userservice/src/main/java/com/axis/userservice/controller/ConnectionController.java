package com.axis.userservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axis.userservice.entity.Connection;
import com.axis.userservice.entity.dto.Requests;
import com.axis.userservice.service.ConnectionService;

@RestController
@RequestMapping("/connect")
public class ConnectionController {
	
	@Autowired
	ConnectionService connectionService;

	  @PostMapping("/request/{user_id}")
	    public ResponseEntity sendRequest(@RequestParam String username, @RequestBody Requests requests, @PathVariable int user_id) {
	        connectionService.sendRequest(username, requests,user_id);
	        return ResponseEntity.ok("request sent successfully");
	    }

	    @PatchMapping("/request-action/{user_id}")
	    public ResponseEntity ActionRequest(@PathVariable int user_id, @RequestBody Requests requests, @RequestParam String username) {
	             
	        if(connectionService.ActionRequest(user_id, requests, username)) {
	        return ResponseEntity.ok("request updated");
	    }
	        else {
	        	return ResponseEntity.ok("request failed");
	        }
			}
	    
	    
	    @GetMapping("/get/{user_id}")
	    public ResponseEntity getConnectionById(@PathVariable int user_id) {
	    	Connection connection = connectionService.getConnectionById(user_id);
			return ResponseEntity.ok(connection);
			 
	    	
	    }
}
