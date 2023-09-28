package com.maveric.datavisualization.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long txnId;
    private long userId;
    private String cardType;
    private double amount;
    private String expType;
    private String bank;
    private String city;
    private String gender;
    private String status;
    @CreationTimestamp
    private LocalDate txnTime;
    @UpdateTimestamp
    private LocalDate updatedTime;



}
