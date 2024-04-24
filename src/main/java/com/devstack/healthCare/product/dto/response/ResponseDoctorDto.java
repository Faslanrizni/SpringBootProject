package com.devstack.healthCare.product.dto.response;

import lombok.Data;
import lombok.Setter;

@Setter
@Data
public class ResponseDoctorDto {
    private long id;
    private String name;
    private String address;
    private String contact;
    private double salary;
}
