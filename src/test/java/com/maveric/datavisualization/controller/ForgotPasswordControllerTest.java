package com.maveric.datavisualization.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.maveric.datavisualization.controllers.ForgotPasswordController;
import com.maveric.datavisualization.entities.PasswordModel;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.services.ForgotPasswordService;

import jakarta.servlet.http.HttpServletRequest;
@ExtendWith(MockitoExtension.class)
@Disabled
class ForgotPasswordControllerTest {

	    @Mock
	    private ForgotPasswordService forgotPasswordService;

	    @InjectMocks
	    private ForgotPasswordController forgotPasswordController;

	    @Mock
	    private HttpServletRequest request;

	    @Test
	    void testUpdatePassword_Success() {
	        // Arrange
	        PasswordModel passwordModel = new PasswordModel("validemail@example.com", "newpassword");

	        // Act
	        ResponseEntity<String> response = forgotPasswordController.updatePassword(passwordModel, request);

	        // Assert
	        assertEquals(200, response.getStatusCode());
	        assertEquals("Password changed successfully", response.getBody());
	        verify(forgotPasswordService, times(1)).updatePassword(passwordModel);
	    }

	   
	}







