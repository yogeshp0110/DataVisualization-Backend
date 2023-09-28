package com.maveric.datavisualization.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.maveric.datavisualization.securityConfig.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.maveric.datavisualization.daos.LoginDAO;
import com.maveric.datavisualization.daos.UserDAO;
import com.maveric.datavisualization.dtos.LoginDetailsDTO;
import com.maveric.datavisualization.entities.Login;
import com.maveric.datavisualization.entities.User;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.response.LoginResponse;
import com.maveric.datavisualization.response.LogoutResponse;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginDAO loginDAO;
    
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private SecurityUtils securityUtils;
    
   public LoginResponse login(LoginDetailsDTO loginDTO) 
    {
    	logger.debug("Attempting login for user with email: {}", loginDTO.getEmail());
        Optional<User> userOptional = userDAO.findByEmail(loginDTO.getEmail());

        if (userOptional.isPresent()) 
        {
            User user = userOptional.get();
            String encodedPassword = user.getPassword();

            if (securityUtils.verifyDataMatches(loginDTO.getPassword(),user.getPassword()))
            {
            	logger.debug("Login successful for user with email: {}", loginDTO.getEmail());
                httpSession.setAttribute("loggedInUser", user);
                Login login = new Login();
                login.setUserId(user.getId());
                login.setLoginTime(LocalDateTime.now());
                loginDAO.save(login);
                System.out.println(user.getFirstName());
                return new LoginResponse("Login Success",LocalDateTime.now(),login.getLoginId(),user.getId(),user.getFirstName(), true);
            } 
            else 
            {
            	 logger.debug("Login failed for user with email: {}", loginDTO.getEmail());    
            	 throw new CustomExceptions(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Invalid email and password", null);             
            } 
        }
            else 
            {
            	 logger.debug("User not found with email: {}", loginDTO.getEmail()); 
                 throw new CustomExceptions(HttpStatus.UNAUTHORIZED.value(), "Unauthorized","Invalid email and password", null);
            }
        }
   
   @Override
   public LogoutResponse logout(Long loginId, Long userId) {
        Optional<Login> loginRecordOptional = loginDAO.findByloginIdAndUserId(loginId, userId);      
           if (loginRecordOptional.isPresent()) {
               Login loginRecord = loginRecordOptional.get();
               httpSession.removeAttribute("loggedInUser");
               // Update the loginTime field with the current date and time
               loginRecord.setLogoutDateTime(LocalDateTime.now());
          
               // Save the updated login record
               loginDAO.save(loginRecord);
               return new LogoutResponse("Logged out successfully", LocalDateTime.now(), true);
           } else {
               throw new CustomExceptions(HttpStatus.NOT_FOUND.value(), "Not Found", "Login record not found", null);
           }
   }
}


	
