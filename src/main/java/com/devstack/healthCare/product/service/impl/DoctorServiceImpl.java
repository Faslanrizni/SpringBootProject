package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;
import com.devstack.healthCare.product.entity.Doctor;
import com.devstack.healthCare.product.repo.DoctorRepo;
import com.devstack.healthCare.product.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepo doctorRepo;

    @Autowired/*inject purpose*/
    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public void createDoctor(RequestDoctorDto dto) {

        UUID uuid = UUID.randomUUID();
        long docId = uuid.getMostSignificantBits();

        Doctor doctor = new Doctor(
                docId, dto.getName(), dto.getAddress(), dto.getContact(), dto.getSalary()
        );
        doctorRepo.save(doctor);

    }

    @Override
    public ResponseDoctorDto getDoctor(long id) {
        Optional<Doctor> selectedDoctor = doctorRepo.findById(id);/*Optional => to work with null values*/
        if (selectedDoctor.isEmpty()){
            throw new RuntimeException("Doctor not found");
        }
        Doctor doctor = selectedDoctor.get();
        return new ResponseDoctorDto(
                doctor.getId(),doctor.getName(),doctor.getAddress(),doctor.getContact(), doctor.getSalary()
        );
    }

    @Override
    public void deleteDoctor(long id) {
        Optional<Doctor> selectedDoctor = doctorRepo.findById(id);/*Optional => to work with null values*/
        if (selectedDoctor.isEmpty()){
            throw new RuntimeException("Doctor not found");
        }
        doctorRepo.deleteById(selectedDoctor.get().getId());

    }

    @Override
    public List<ResponseDoctorDto> findDoctorsByName(String name) {
        List<Doctor> allByName = doctorRepo.findAllByName(name);
        return null;
    }

    @Override
    public void updateDoctor(long id, RequestDoctorDto dto) {

    }
}
