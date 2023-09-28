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
public class LoginResponse {

	 private String message;
	 private LocalDateTime loginDateTime;
	 private Long loginId;
	 private Long userId;
	 private String firstName;
	 private Boolean status;
	 
}
