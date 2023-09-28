package com.maveric.datavisualization.dtos.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetailsDTO {


	private Long userId;
	private String email;
	private LocalDateTime loginTime; //just loginTime
	private LocalDateTime logoutDateTime; 
	private String password;
	
	
	
	
	
}
