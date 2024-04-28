package com.devstack.healthCare.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole {
    @Id
    private long id;
    private String roleName;
    private String description;

    @OneToMany(mappedBy = "userRole")
    private Set<UserRoleHasUser>userRoleHasUsers;

}
