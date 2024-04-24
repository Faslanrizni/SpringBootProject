package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
}
