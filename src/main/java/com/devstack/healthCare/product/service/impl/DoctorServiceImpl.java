package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;
import com.devstack.healthCare.product.entity.Doctor;
import com.devstack.healthCare.product.repo.DoctorRepo;
import com.devstack.healthCare.product.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepo doctorRepo;

    @Autowired/*inject purpose*/
    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public void createDoctor(RequestDoctorDto dto) {
        Doctor doctor = new Doctor(
                "", dto.getName(), dto.getAddress(), dto.getContact(), dto.getSalary()
        );
        doctorRepo.save(doctor);

    }

    @Override
    public ResponseDoctorDto getDoctor(long id) {
        return null;
    }

    @Override
    public void deleteDoctor(long id) {

    }

    @Override
    public List<ResponseDoctorDto> findDoctorsByName(String name) {
        return null;
    }

    @Override
    public void updateDoctor(long id, RequestDoctorDto dto) {

    }
}
