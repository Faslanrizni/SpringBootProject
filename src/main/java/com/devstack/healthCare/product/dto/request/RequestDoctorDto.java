package com.devstack.healthCare.product.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*@AllArgsConstructor
@NoArgsConstructor
@ToString*/
@Data /*to oya 3ma wenuwata*/
public class RequestDoctorDto {
    private String name;
    private String address;
    private String contact;
    private double salary;
}
