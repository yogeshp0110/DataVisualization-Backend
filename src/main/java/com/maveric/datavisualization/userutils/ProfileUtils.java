package com.maveric.datavisualization.userutils;

public class ProfileUtils {

    public static String maskAadhaar(String aadhaarNumber) {
        String maskedDigits = "********";
        String lastFourDigits = aadhaarNumber.substring(8);

        return maskedDigits + lastFourDigits;
    }




    public static String maskPan(String panNumber) {
        String maskedDigits = "******";
        String lastFourDigits = panNumber.substring(6);

        return maskedDigits + lastFourDigits;
    }

}
