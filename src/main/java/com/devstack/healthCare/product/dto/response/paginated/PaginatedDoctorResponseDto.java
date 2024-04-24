package com.devstack.healthCare.product.dto.response.paginated;

import com.devstack.healthCare.product.dto.response.ResponseDoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedDoctorResponseDto {
    private long count;
    private List<ResponseDoctorDto> dataList;




}
