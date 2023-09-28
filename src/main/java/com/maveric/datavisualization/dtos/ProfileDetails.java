package com.maveric.datavisualization.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetails {

    private long id;
    @NotBlank

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN card number")
    private String panCard;
    @NotBlank

    @Pattern(regexp = "^[0-9]{12}$", message = "Invalid Aadhaar number")
    private String adharCard;
    @NotBlank
    private String qualification;
    @NotBlank

    private String employmentStatus;
    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid Pincode")
    private String pincode;

    private Timestamp creationDate;
    private Timestamp modifiedDate;
    private AddressDetails address;


}
