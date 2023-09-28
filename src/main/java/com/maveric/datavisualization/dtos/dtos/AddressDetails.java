package com.maveric.datavisualization.dtos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetails {

    private Long id;
    private String homeAddress;
    private String officeAddress;

}
