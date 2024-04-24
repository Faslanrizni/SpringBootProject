package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.Exception.EntryNotFoundException;
import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;
import com.devstack.healthCare.product.dto.response.paginated.PaginatedDoctorResponseDto;
import com.devstack.healthCare.product.entity.Doctor;
import com.devstack.healthCare.product.repo.DoctorRepo;
import com.devstack.healthCare.product.service.DoctorService;
import com.devstack.healthCare.product.util.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service

public class DoctorServiceImpl implements DoctorService {



    private final DoctorMapper doctorMapper;
    private final DoctorRepo doctorRepo;

    @Autowired/*inject purpose*/
    public DoctorServiceImpl( DoctorRepo doctorRepo, DoctorMapper doctorMapper) {
        this.doctorRepo = doctorRepo;
        this.doctorMapper = doctorMapper;

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
            throw new EntryNotFoundException("Doctor not found");
        }
        Doctor doctor = selectedDoctor.get();
        return doctorMapper.toResponseDoctorDto(selectedDoctor.get());

        /*return new ResponseDoctorDto(
                doctor.getId(),doctor.getName(),doctor.getAddress(),doctor.getContact(), doctor.getSalary()
        );*/
    }

    @Override
    public void deleteDoctor(long id) {
        Optional<Doctor> selectedDoctor = doctorRepo.findById(id);/*Optional => to work with null values*/
        if (selectedDoctor.isEmpty()){
            throw new EntryNotFoundException("Doctor not found");
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
        Optional<Doctor> selectedDoctor = doctorRepo.findById(id);/*Optional => to work with null values*/
        if (selectedDoctor.isEmpty()){
            throw new EntryNotFoundException("Doctor not found");
        }
        Doctor doctor = selectedDoctor.get();
        doctor.setName(dto.getName());
        doctor.setAddress(dto.getAddress());
        doctor.setSalary(dto.getSalary());
        doctor.setContact(dto.getContact());

        doctorRepo.save(doctor);
    }

    @Override
    public PaginatedDoctorResponseDto getAllDoctors(String searchText, int page, int size) {
        searchText="%"+searchText+"%";
        List<Doctor> doctors = doctorRepo.searchDoctors(searchText, PageRequest.of(page, size));
        long doctorCount = doctorRepo.countDoctors(searchText);
        List <ResponseDoctorDto> dtos = doctorMapper.toResponseDoctorDtoList(doctors);
        /*doctors.forEach(doctor -> {
            dtos.add(
                    new ResponseDoctorDto(
                            doctor.getId(),doctor.getName(),doctor.getAddress(),
                            doctor.getContact(), doctor.getSalary()
                    )
            );
        });*/

        return new PaginatedDoctorResponseDto(
                doctorCount,
                dtos
        );
    }
}

