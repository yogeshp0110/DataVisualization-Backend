package com.maveric.datavisualization.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.datavisualization.entities.PasswordModel;
import com.maveric.datavisualization.services.ForgotPasswordService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class ForgotPasswordController {
	
	 private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
	@Autowired
	private ForgotPasswordService forgotPwdservice;

	@PostMapping("/forgot-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordModel passwordModel,HttpServletRequest request) {
		
		logger.info("ForgotPasswordController-registerUser() call started");
        logger.debug("User Details : {}, Uri : {},",passwordModel.getEmail(), request.getRequestURI());
        forgotPwdservice.updatePassword(passwordModel);
        return new ResponseEntity<>("Password changed successfully", HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
	
		
	}

