package com.devstack.healthCare.product.service;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;

import java.util.List;

public interface DoctorService  {

        public void createDoctor(RequestDoctorDto dto);
        public ResponseDoctorDto getDoctor(long id);
        public void deleteDoctor(long id);
        public List<ResponseDoctorDto> findDoctorsByName(String name);
        public void updateDoctor(long id, RequestDoctorDto dto);

}

