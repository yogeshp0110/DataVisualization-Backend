package com.maveric.datavisualization.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.datavisualization.dtos.LoginDetailsDTO;
import com.maveric.datavisualization.response.LoginResponse;
import com.maveric.datavisualization.services.LoginService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class LoginController {

	 private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	   
	
	 @Autowired
	 private LoginService loginService;
	 
	 
	 @PostMapping(path = "/login" )
	    public ResponseEntity<?> login(@RequestBody LoginDetailsDTO loginDTO)
	    {
		   logger.info("LoginController:: login() - Call strted");  
	       LoginResponse loginResponse = loginService.login(loginDTO);
	       logger.info("LoginController:: login() - Call Ended");  
	       return ResponseEntity.ok(loginResponse);
	    }
	 


	
}
