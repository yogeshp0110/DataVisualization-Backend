package com.maveric.datavisualization.controller;


import com.maveric.datavisualization.controllers.LoginController;
import com.maveric.datavisualization.dtos.LoginDetailsDTO;
import com.maveric.datavisualization.response.LoginResponse;
import com.maveric.datavisualization.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccessful() {
        // Arrange
        LoginDetailsDTO loginDTO = new LoginDetailsDTO();
        loginDTO.setEmail("john123@gmail.com");
        loginDTO.setPassword("John@123");

        LoginResponse expectedResponse = new LoginResponse("Login Success", null, null, null, "John", true);

        when(loginService.login(loginDTO)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> responseEntity = loginController.login(loginDTO);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        LoginResponse actualResponse = (LoginResponse) responseEntity.getBody();
        assertEquals("Login Success", actualResponse.getMessage());
        assertEquals("John", actualResponse.getFirstName()); // Asserting the first name
        assertTrue(actualResponse.getFirstName() != null && !actualResponse.getFirstName().isEmpty());

        // Verify that loginService.login was called
        verify(loginService).login(loginDTO);
    }
}
