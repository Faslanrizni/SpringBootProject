package com.devstack.healthCare.product.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordConfig {
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);/*10 =>encryption length*/

    }
}
