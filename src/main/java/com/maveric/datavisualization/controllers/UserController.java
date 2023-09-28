package com.maveric.datavisualization.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maveric.datavisualization.dtos.UserDetails;
import com.maveric.datavisualization.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetails> registerUser(@RequestBody @Valid UserDetails registerUser, HttpServletRequest request) {
        logger.debug("UserController-registerUser() call started");
        logger.debug("User Details : {}, Uri : {},",registerUser, request.getRequestURI());
        return new ResponseEntity<>(userService.registerUser(registerUser), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDetails> getUser(@PathVariable Long id,HttpServletRequest request) {
        logger.debug("UserController-getUser() call started");
        logger.debug("User id : {}, Uri : {},",id, request.getRequestURI());
        return new ResponseEntity<>(userService.getUser(id), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetails> updateUser(@RequestBody UserDetails updateUser,HttpServletRequest request) {
        logger.debug("UserController-updateUser() call started");
        logger.debug("User Details : {}, Uri : {},",updateUser, request.getRequestURI());
        return new ResponseEntity<>(userService.updateUser(updateUser), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id,HttpServletRequest request) {
        logger.debug("UserController-deleteUser() call started");
        logger.debug("User id : {}, Uri : {},",id, request.getRequestURI());
        userService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
