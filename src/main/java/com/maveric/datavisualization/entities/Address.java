package com.maveric.datavisualization.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address  {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "homeaddress")
    private String homeAddress;
    @Column(name = "officeaddress")
    private String officeAddress;

}
