package com.maveric.datavisualization.services;

import com.maveric.datavisualization.daos.UserDAO;
import com.maveric.datavisualization.dtos.UserDetails;
import com.maveric.datavisualization.entities.User;
import com.maveric.datavisualization.exception.CustomExceptions;
import com.maveric.datavisualization.securityConfig.SecurityUtils;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails registerUser(UserDetails registerUser) {
        Optional<User> useropt = userDAO.findByEmail(registerUser.getEmail());
        if (useropt.isPresent()) {
            logger.error("UserServiceImpl-registerUser() - email id already registered{}", registerUser.getEmail());
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "Email Already registered", "Please login", null);
        }
        if (userDAO.getUserWithContactNo(registerUser.getContactNo(), registerUser.getCountryCode()) != null) {
            logger.error("UserServiceImpl-registerUser() -contact number already registered{}", registerUser.getEmail());
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "ContactNumber Already registered ,Please login", "Please login", null);
        }
        User user = new User();
        BeanUtils.copyProperties(registerUser, user);
        user.setPassword(securityUtils.encryptData(user.getPassword()));
        user = userDAO.save(user);
        logger.debug("UserServiceImpl-registerUser() data saved in DB{}", user);

        return User.convertToRegistrationDetails(user);
    }

    public UserDetails updateUser(UserDetails registerUser) {
        Optional<User> user = userDAO.findById(registerUser.getId());
        if (user.isEmpty()) {
            logger.error("UserServiceImpl-updateUser(): user details not found  with id of{}", registerUser.getId());
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "User details not found ", "", null);
        }
        User userRegistration = user.get();
        if (StringUtils.isNotBlank(userRegistration.getFirstName())) {
            userRegistration.setFirstName(registerUser.getFirstName());
        }
        if (StringUtils.isNotBlank(userRegistration.getLastName())) {
            userRegistration.setLastName(registerUser.getLastName());
        }
        if (StringUtils.isNotBlank(userRegistration.getDob())) {
            userRegistration.setDob(registerUser.getDob());
        }
        BeanUtils.copyProperties(registerUser, userRegistration);
        userRegistration = userDAO.saveAndFlush(userRegistration);
        logger.debug("UserServiceImpl-updateUser(): data saved in DB-{}", userRegistration);
        logger.debug("UserServiceImpl-updateUser() call ended");

        return User.convertToRegistrationDetails(userRegistration);
    }

    public UserDetails getUser(Long id) {
        Optional<User> userRegistration = userDAO.findById(id);
        if (userRegistration.isEmpty()) {
            logger.error("UserServiceImpl-getUser(): user details not found  with id of -{}", id);
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "User details not found ", "Please check again", null);
        }
        User registration = userRegistration.get();
        logger.debug("UserServiceImpl-getUser(): data fetch from DB{}", userRegistration);
        logger.debug("UserServiceImpl-getUser() call ended");
        return User.convertToRegistrationDetails(registration);

    }

    public void deleteUser(Long id) {
        Optional<User> userRegistration = userDAO.findById(id);
        logger.debug("UserServiceImpl-deleteUser(): data fetch from DB{}", userRegistration);
        if (userRegistration.isEmpty()) {
            logger.error("UserServiceImpl-deleteUser():user details not found  with id of - {}", id);
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "User details not found ", "Please check again", null);
        }
        userDAO.delete(userRegistration.get());
        logger.debug("UserServiceImpl-deleteUser() call ended");

    }
}
