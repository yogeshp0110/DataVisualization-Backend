package com.maveric.datavisualization.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder

public class Profile {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    private  Long id;
    @Column(name = "pancard")
    private String panCard;
    @Column(name = "adharcard")
    private String adharCard;
    @Column(name="qualification")
    private String qualification;
    @Column(name="employmentstatus")
    private String employmentStatus;
    @Column(name="pincode")
    private String pincode;
    @CreationTimestamp
    @Column(name="creationdate")
    private Timestamp creationDate;
    @UpdateTimestamp
    @Column(name="modifieddate")
    private Timestamp modifiedDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;



}
