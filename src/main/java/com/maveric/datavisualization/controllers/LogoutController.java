package com.maveric.datavisualization.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.datavisualization.response.LogoutResponse;
import com.maveric.datavisualization.services.LoginService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class LogoutController {

	private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
		
	@Autowired
	private LoginService loginService;
	
	 
	@PostMapping(path = "/logout/{loginId}/{userId}")
	public ResponseEntity<LogoutResponse> logout(@PathVariable Long loginId, @PathVariable Long userId)
	{
		logger.info("LogoutController:: logout() - Call strted");  
	    LogoutResponse logoutResponse = loginService.logout(loginId, userId);
	    logger.info("LogoutController:: logout() - Call End"); 
	    return ResponseEntity.ok(logoutResponse);
	}
}
