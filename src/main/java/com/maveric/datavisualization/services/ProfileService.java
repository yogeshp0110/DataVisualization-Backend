package com.maveric.datavisualization.services;

import com.maveric.datavisualization.dtos.ProfileDetails;


public interface ProfileService {
    public ProfileDetails updateProfile(ProfileDetails updateProfile);

    public ProfileDetails createProfile(ProfileDetails profile);

    public ProfileDetails getProfile(Long id);

    public void deleteProfile(Long id);
}
