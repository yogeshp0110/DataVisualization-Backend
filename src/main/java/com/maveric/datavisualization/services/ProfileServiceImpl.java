package com.maveric.datavisualization.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.datavisualization.daos.ProfileDAO;
import com.maveric.datavisualization.dtos.ProfileDetails;
import com.maveric.datavisualization.entities.Address;
import com.maveric.datavisualization.entities.Profile;
import com.maveric.datavisualization.exception.CustomExceptions;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.maveric.datavisualization.userutils.ProfileUtils.*;


@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ProfileDetails createProfile(ProfileDetails profileDetails) {
        Profile profile = null;
        try {
            profile = profileDAO.save(objectMapper.convertValue(profileDetails, Profile.class));
            profile.setAdharCard(maskAadhaar(profile.getAdharCard()));
            profile.setPanCard(maskPan(profile.getPanCard()));


        } catch (Exception e) {
            e.getStackTrace();
        }

        return objectMapper.convertValue(profile, ProfileDetails.class);
    }


    @Override
    public ProfileDetails updateProfile(ProfileDetails updateProfile) {

        Profile profile1 = null;
        try {
            Optional<Profile> profileDetails = profileDAO.findById(Long.valueOf(updateProfile.getId()));
            if (!profileDetails.isPresent()) {
                logger.error("ProfileServiceImpl-updateProfile(): profile details not found  with id of{}", updateProfile.getId());
                throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "Profile details not found ", "", null);
            }
            profile1 = profileDetails.get();
            logger.debug("ProfileServiceImpl-updateProfile(): ", profile1);

            if (StringUtils.isNotBlank(updateProfile.getQualification())) {
                profile1.setQualification(updateProfile.getQualification());
            }

            if (StringUtils.isNotBlank(String.valueOf((updateProfile.getEmploymentStatus())))) {
                profile1.setEmploymentStatus(updateProfile.getEmploymentStatus());
            }
            if (StringUtils.isNotBlank(String.valueOf(updateProfile.getPincode()))) {
                profile1.setPincode(updateProfile.getPincode());


            }

            if (updateProfile.getAddress() != null) {
                Address address = new Address(null, updateProfile.getAddress().getHomeAddress(), updateProfile.getAddress().getOfficeAddress());
                profile1.setAddress(address);
            }

            profile1 = profileDAO.saveAndFlush(profile1);
            logger.debug("ProfileServiceImpl-updateProfile(): data saved in DB-{}", profile1);
            logger.debug("ProfileServiceImpl-updateProfile(): data saved in DB-{}() call ended");
        } catch (Throwable throwable) {

        }
        profile1.setAdharCard(maskAadhaar(profile1.getAdharCard()));
        profile1.setPanCard(maskPan(profile1.getPanCard()));

        return objectMapper.convertValue(profile1, ProfileDetails.class);

    }


    @Override
    public ProfileDetails getProfile(Long id) {
        Optional<Profile> profileDetails = profileDAO.findById(id);
        if (!profileDetails.isPresent()) {
            logger.error("ProfileServiceImpl-getProfile(): profile details not found  with id of -{}", id);
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "Profile details not found ", "Please check again", null);
        }
        Profile profile = profileDetails.get();
        logger.debug("ProfileServiceImpl-getUser(): data fetch from DB{}", profile);
        profile.setAdharCard(maskAadhaar(profile.getAdharCard()));

        profile.setPanCard(maskPan(profile.getPanCard()));

        logger.debug("ProfileServiceImpl-getUser() call ended");
        return objectMapper.convertValue(profile, ProfileDetails.class);

    }

    @Override
    public void deleteProfile(Long id) {
        Optional<Profile> profileDetails = profileDAO.findById(id);
        logger.debug("ProfileServiceImpl-deleteProfile(): data fetch from DB{}", profileDetails);
        if (!profileDetails.isPresent()) {
            logger.error("ProfileServiceImpl-deleteProfile():profile details not found  with id of - {}", id);
            throw new CustomExceptions(HttpStatus.BAD_REQUEST.value(), "Profile details not found ", "Please check again", null);
        }
        profileDAO.deleteById(profileDetails.get().getId());
        logger.debug("ProfileServiceImpl-deleteProfile() call ended");

    }
}


