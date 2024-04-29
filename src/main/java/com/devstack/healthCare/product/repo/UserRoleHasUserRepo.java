package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.UserRole;
import com.devstack.healthCare.product.entity.UserRoleHasUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRoleHasUserRepo extends JpaRepository<UserRole,Long> {

    @Query(value = "SELECT * FROM user_role_has_user WHERE user_id=?1",nativeQuery = true)
    List<UserRoleHasUser> findByUserId(long id);

}