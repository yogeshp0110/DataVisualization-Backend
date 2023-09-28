package com.maveric.datavisualization.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encryptData(String data){

       return passwordEncoder.encode(data);
    }

    public boolean verifyDataMatches(String data, String encryptedData){
        return passwordEncoder.matches(data,encryptedData);
    }


}
