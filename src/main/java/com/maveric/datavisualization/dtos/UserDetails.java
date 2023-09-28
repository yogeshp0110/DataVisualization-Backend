package com.maveric.datavisualization.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class UserDetails {

    private long id;
    @Length(min=3,max=15)
    @NotBlank()
    private String firstName;
    @Length(min=3,max=15)
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[\\w]+$", message = "Invalid email")
    private String email;
    @NotBlank
    @Pattern(regexp= "[+][0-9]{1,3}",message = "Invalid Country Code")
    private String countryCode;
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid contact Number")
    private String contactNo;
    @NotBlank
    private String gender;
    private String dob;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$", message = "Password must contain at least one uppercase letter, one special character, one digit, and be at least 8 characters long\")\r\n"
			+ " ") 
    private String password;
    private String kycStatus;
    private LocalDate redDate;
    private LocalDate modifiedDate;

}
