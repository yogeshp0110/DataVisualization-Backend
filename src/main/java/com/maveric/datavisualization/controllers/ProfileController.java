package com.maveric.datavisualization.controllers;

import com.maveric.datavisualization.dtos.ProfileDetails;
import com.maveric.datavisualization.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
@Autowired
    private ProfileService profileService;
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileDetails> createProfile(@RequestBody  @Valid ProfileDetails createProfile, HttpServletRequest request) {
        logger.debug("ProfileController-createProfile() call started");
        logger.debug("Profile Details : {}, Uri : {},",createProfile, request.getRequestURI());
        return new ResponseEntity<>(profileService.createProfile(createProfile), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDetails> getProfile(@PathVariable Long id,HttpServletRequest request) {
        logger.debug("ProfileController-getProfile() call started");
        logger.debug("User id : {}, Uri : {},",id, request.getRequestURI());
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileDetails> updateProfile(@RequestBody ProfileDetails updateProfile,HttpServletRequest request) {
        logger.debug("ProfileController-updateProfile() call started");
        logger.debug("Profile Details : {}, Uri : {},",updateProfile, request.getRequestURI());
        return new ResponseEntity<>(profileService.updateProfile(updateProfile), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id,HttpServletRequest request) {
        logger.debug("ProfileController-deleteProfile() call started");
        logger.debug("Profile id : {}, Uri : {},",id, request.getRequestURI());
        profileService.deleteProfile(id);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }



}
