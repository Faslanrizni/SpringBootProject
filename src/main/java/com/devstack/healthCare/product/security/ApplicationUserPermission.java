package com.devstack.healthCare.product.security;

public enum ApplicationUserPermission {
    DOCTOR_READ("doctor:read"),
    DOCTOR_WRITE("doctor:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
