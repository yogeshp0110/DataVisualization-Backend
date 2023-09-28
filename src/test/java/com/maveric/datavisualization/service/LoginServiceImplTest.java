package com.maveric.datavisualization.service;


import com.maveric.datavisualization.daos.LoginDAO;
import com.maveric.datavisualization.daos.UserDAO;
import com.maveric.datavisualization.dtos.LoginDetailsDTO;
import com.maveric.datavisualization.entities.Login;
import com.maveric.datavisualization.entities.User;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.response.LoginResponse;
import com.maveric.datavisualization.response.LogoutResponse;
import com.maveric.datavisualization.securityConfig.SecurityUtils;
import com.maveric.datavisualization.services.LoginServiceImpl;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private LoginDAO loginDAO;

    @Mock
    private HttpSession httpSession;

    @Mock
    private SecurityUtils securityUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccessful() {
        // Arrange
        LoginDetailsDTO loginDTO = new LoginDetailsDTO();
        loginDTO.setEmail("john123@gmail.com");
        loginDTO.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setPassword("John@123"); 

        when(userDAO.findByEmail("john123@gmail.com")).thenReturn(Optional.of(user));
        when(securityUtils.verifyDataMatches("password", "John@123")).thenReturn(true);

        // Act
        LoginResponse response = loginService.login(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Login Success", response.getMessage());
        assertNotNull(response.getLoginDateTime());
        assertNotNull(response.getLoginDateTime());
        assertEquals(1L, response.getUserId());
        assertEquals("John", response.getFirstName());
        assertTrue(response.getFirstName() != null && !response.getFirstName().isEmpty());

        // Verify that HttpSession.setAttribute and loginDAO.save were called
        verify(httpSession).setAttribute("loggedInUser", user);
        verify(loginDAO).save(any(Login.class));
    }

    @Test
    public void testLoginInvalidPassword() {
        // Arrange
        LoginDetailsDTO loginDTO = new LoginDetailsDTO();
        loginDTO.setEmail("john123@gmail.com");
        loginDTO.setPassword("password");

        User user = new User();
        user.setPassword("John@123"); 

        when(userDAO.findByEmail("john123@gmail.com")).thenReturn(Optional.of(user));
        when(securityUtils.verifyDataMatches("password", "John@123")).thenReturn(false);

        // Act and Assert
        assertThrows(CustomExceptions.class, () -> loginService.login(loginDTO));
    }

    @Test
    public void testLoginUserNotFound() {
        // Arrange
        LoginDetailsDTO loginDTO = new LoginDetailsDTO();
        loginDTO.setEmail("nonexistent@gmail.com");
        loginDTO.setPassword("password");

        when(userDAO.findByEmail("nonexistent@gmail.com")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CustomExceptions.class, () -> loginService.login(loginDTO));
    }

    @Test
    public void testLogoutSuccessful() {
        // Arrange
        Long loginId = 1L;
        Long userId = 1L;

        Login loginRecord = new Login();
        loginRecord.setLoginId(loginId);

        when(loginDAO.findByloginIdAndUserId(loginId, userId)).thenReturn(Optional.of(loginRecord));

        // Act
        LogoutResponse response = loginService.logout(loginId, userId);

        // Assert
        assertNotNull(response);
        assertEquals("Logged out successfully", response.getMessage());
        assertNotNull(response.getLogoutDateTime());

        // Verify that HttpSession.removeAttribute and loginDAO.save were called
        verify(httpSession).removeAttribute("loggedInUser");
        verify(loginDAO).save(loginRecord);
        assertNotNull(loginRecord.getLogoutDateTime());
    }

    @Test
    public void testLogoutLoginRecordNotFound() {
        // Arrange
        Long loginId = 1L;
        Long userId = 1L;

        when(loginDAO.findByloginIdAndUserId(loginId, userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CustomExceptions.class, () -> loginService.logout(loginId, userId));
    }
}