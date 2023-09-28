package com.maveric.datavisualization.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDetails {

    private long txnId;
    @Positive
    private long userId;
    @NotBlank
    private String bank;
    @NotBlank
    private String cardType;
    @Positive
    private double amount;
    @NotBlank
    private String expType;
    @NotBlank
    private String city;
    @NotBlank
    private String gender;
    @NotBlank
    private String status;
    private LocalDate txnTime;
    private LocalDate updatedTime;

    public static final String FILTER_TXN_BY_EXPANSE = "FILTER_TXN_BY_EXPANSE";
    public static final String FILTER_TXN_BY_BANK = "FILTER_TXN_BY_BANK";
    public static final String FILTER_TXN_BY_CARD = "FILTER_TXN_BY_CARD";
    public static final String FILTER_TXN_BY_GENDER = "FILTER_TXN_BY_GENDER";
    public static final String FILTER_TXN_BY_STATUS = "FILTER_TXN_BY_STATUS";

    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";


    public static final String TXN_STATUS_PENDING = "PENDING";
    public static final String TXN_STATUS_COMPLETED = "COMPLETED";
    public static final String TXN_STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String TXN_STATUS_FAILED = "FAILED";

    public static final String CARD_TYPE_VISA = "VISA";
    public static final String CARD_TYPE_MASTER = "MASTER";
    public static final String CARD_TYPE_RUPAY = "RUPAY";


}
