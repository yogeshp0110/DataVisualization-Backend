package com.maveric.datavisualization.dtos;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
