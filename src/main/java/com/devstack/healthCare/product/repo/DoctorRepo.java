package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {
}
