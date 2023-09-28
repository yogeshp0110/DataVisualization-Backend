package com.maveric.datavisualization.entities;

import com.maveric.datavisualization.dtos.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String email;
    @Column(name = "countrycode")
    private String countryCode;
    @Column(name = "contactno")
    private String contactNo;
    private String gender;
    private String dob;
    private String password;
    @CreationTimestamp
    @Column(name = "regdate")
    private LocalDate redDate;
    @UpdateTimestamp
    @Column(name = "modifieddate")
    private LocalDate modifiedDate;
    @Column(name = "kycstatus")
    private String kycStatus;


    public static List<UserDetails> convertToRegistrationDetails(List<User> users){

        List<UserDetails> userDetails =new ArrayList<>();
        for (User user : users){
            userDetails.add(convertToRegistrationDetails(user));
        }
        return userDetails;

    }

    public static UserDetails convertToRegistrationDetails(User user) {
        UserDetails userDetails = new UserDetails();
        BeanUtils.copyProperties(user, userDetails);
        return userDetails;

    }

}
