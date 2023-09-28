package com.maveric.datavisualization.services;

import com.maveric.datavisualization.dtos.LoginDetailsDTO;
import com.maveric.datavisualization.response.LoginResponse;
import com.maveric.datavisualization.response.LogoutResponse;

import jakarta.servlet.http.HttpSession;

public interface LoginService {


	

	

	public LoginResponse login(LoginDetailsDTO loginDTO);

	public LogoutResponse logout(Long loginId,Long userId);

}
