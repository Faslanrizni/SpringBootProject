package com.devstack.healthCare.product.jwt;

import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
