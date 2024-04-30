package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.entity.UserRole;
import com.devstack.healthCare.product.repo.UserRoleRepo;
import com.devstack.healthCare.product.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepo;
    @Override
    public void initializeRoles() {
        UserRole admin = new UserRole(
                1,
                "ADMIN",
                "admin",
                null
        );
        UserRole doctor = new UserRole(
                2,
                "DOCTOR",
                "doctor",
                null
        );
        userRoleRepo.saveAll(List.of(admin,doctor));
    }
}
