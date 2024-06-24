// This file contains the controller for the home page of the application.

package com.axis.team4.codecrafters.message_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	// This method is used to handle the request for the home page of the application.
	
	@GetMapping("/")
	public ResponseEntity<String> homePageHandler(){
		
		return new ResponseEntity<String> ("Chat Wave Homepage",HttpStatus.OK);
	}

}