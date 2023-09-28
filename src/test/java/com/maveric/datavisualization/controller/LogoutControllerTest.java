package com.maveric.datavisualization.controller;
import com.maveric.datavisualization.controllers.LogoutController;
import com.maveric.datavisualization.response.LogoutResponse;
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

public class LogoutControllerTest {

    @InjectMocks
    private LogoutController logoutController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogoutSuccessful() {
        // Arrange
        Long loginId = 1L;
        Long userId = 2L;

        LogoutResponse expectedResponse = new LogoutResponse("Logout successful", null, null);

        when(loginService.logout(loginId, userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<LogoutResponse> responseEntity = logoutController.logout(loginId, userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        LogoutResponse actualResponse = responseEntity.getBody();
        assertEquals("Logout successful", actualResponse.getMessage());

        // Verify that loginService.logout was called with the correct parameters
        verify(loginService).logout(loginId, userId);
    }
}
    
   
