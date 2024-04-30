package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.dto.request.RequestUserDto;
import com.devstack.healthCare.product.entity.UserRole;
import com.devstack.healthCare.product.entity.UserRoleHasUser;
import com.devstack.healthCare.product.repo.UserRoleHasUserRepo;
import com.devstack.healthCare.product.repo.UserRoleRepo;
import com.devstack.healthCare.product.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRoleRepo userRoleRepo;

    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public UserServiceImpl(UserRoleRepo userRoleRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRoleRepo = userRoleRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }

    @Override
    public void signup(RequestUserDto userDto) {
        UserRole userRole;

        if (userDto.getId()==1){
            userRole = userRoleRepo.findUserRoleByName("ADMIN");
        }else{
            userRole = userRoleRepo.findUserRoleByName("DOCTOR");
        }
        if (userRole == null){
            throw new RuntimeException("User role has not found");
        }
        UserRoleHasUser userData = new UserRoleHasUser("","");
        userRoleHasUserRepo.save();


    }

    @Override
    public boolean verifyUser(String type, String token) {
        return false;
    }
}
