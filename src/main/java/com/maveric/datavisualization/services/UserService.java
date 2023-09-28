package com.maveric.datavisualization.services;


import com.maveric.datavisualization.dtos.UserDetails;


public interface UserService {


    public UserDetails registerUser(UserDetails registerUser) ;

    public UserDetails updateUser(UserDetails registerUser) ;

    public UserDetails getUser(Long id) ;

    public void deleteUser(Long id) ;
}
