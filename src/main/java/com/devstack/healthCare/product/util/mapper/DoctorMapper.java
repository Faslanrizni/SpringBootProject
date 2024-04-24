package com.devstack.healthCare.product.util.mapper;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;
import com.devstack.healthCare.product.entity.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    ResponseDoctorDto toResponseDoctorDto(Doctor doctor); /*entity => dto*/
    Doctor toDoctor(RequestDoctorDto dto);/*dto => entity*/
    List<ResponseDoctorDto> toResponseDoctorDtoList(List<Doctor> list);
}

/*
mapping(mapping entity to dto, dto to entity)
1) model mapper
2) map struct(optimize one we use map struct for mapping)
* */
