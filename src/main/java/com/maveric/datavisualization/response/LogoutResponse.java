package com.maveric.datavisualization.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponse {


	 private String message;
	 private LocalDateTime logoutDateTime;
	 private Boolean status;
	
}
