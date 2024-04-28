package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.repo.UserRepo;
import com.devstack.healthCare.product.repo.UserRoleHasUserRepo;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl {
    private final UserRepo userRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public ApplicationUserServiceImpl(UserRepo userRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRepo = userRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }
}
