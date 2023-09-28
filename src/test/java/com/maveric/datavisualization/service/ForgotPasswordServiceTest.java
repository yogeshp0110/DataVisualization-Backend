
package com.maveric.datavisualization.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.maveric.datavisualization.daos.UserDAO;
import com.maveric.datavisualization.entities.PasswordModel;
import com.maveric.datavisualization.entities.User;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.securityConfig.SecurityUtils;
import com.maveric.datavisualization.services.ForgotPasswordService;



@ExtendWith(MockitoExtension.class)
class ForgotPasswordServiceTest {

    @Mock
    private UserDAO userDAO;
    
    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;

    @Test
    void testUpdatePassword_ValidEmailAndPassword_Success() {
       
        PasswordModel passwordModel = new PasswordModel("validemail@example.com", "newpassword");
        User existingUser = new User();
        when(userDAO.getByUserEmail(passwordModel.getEmail())).thenReturn(existingUser);

        
        when(securityUtils.encryptData(passwordModel.getPassword())).thenReturn("encryptedPassword");

       
        User updatedUser = forgotPasswordService.updatePassword(passwordModel);

       
        assertEquals("encryptedPassword", updatedUser.getPassword());
        verify(userDAO, times(1)).save(existingUser);
    }

    @Test
    void testUpdatePassword_InvalidEmail_ThrowsException() {
      
        PasswordModel passwordModel = new PasswordModel("invalidemail@example.com", "newpassword");
        when(userDAO.getByUserEmail(passwordModel.getEmail())).thenReturn(null);

        
        CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
            forgotPasswordService.updatePassword(passwordModel);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertEquals("Please enter correct email", exception.getMessage());
    }
    
    




 
   






    
    
}

