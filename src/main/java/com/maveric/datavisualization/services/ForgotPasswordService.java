package com.maveric.datavisualization.services;

import java.util.Optional;

import com.maveric.datavisualization.securityConfig.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.maveric.datavisualization.controllers.UserController;
import com.maveric.datavisualization.daos.UserDAO;
import com.maveric.datavisualization.entities.PasswordModel;
import com.maveric.datavisualization.entities.User;
import com.maveric.datavisualization.exception.CustomExceptions;

@Service
public class ForgotPasswordService {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	 @Autowired
	 private UserDAO userDAO;
	 
	 @Autowired
	 private SecurityUtils securityUtils;
	 
	 public User updatePassword(PasswordModel passwordModel)
	 {
		
		User user1= userDAO.getByUserEmail(passwordModel.getEmail());
			
			logger.info("email form service "+ passwordModel.getEmail());
			
			logger.info("User:"+user1);
			
			if(!(user1==null))
			{
				if(!(passwordModel.getPassword()==null)) {
					
					user1.setPassword(securityUtils.encryptData(passwordModel.getPassword()));
					userDAO.save(user1);
				}
				else {
					
					logger.info("UserServiceImpl-registerUser()-redirect to resetpassword page");
					System.out.println("Email exist redirecting to reset passowrd page");
					throw new CustomExceptions(HttpStatus.OK.value(), "User email exist in database", "Redirecting to password reset page", passwordModel.getEmail());
				}
			}
			else
			{
				System.out.println("email id is not exist");
				logger.error("UserServiceImpl-registerUser() - email id is not exist");
				throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), " Invalid Email ID  ", "Please enter correct email",  null);
			}
			
			return user1;
	 		
	 }
 }
