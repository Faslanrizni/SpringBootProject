package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.dto.request.RequestUserDto;
import com.devstack.healthCare.product.service.UserService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @PostMapping("/visitors/signup")/*default data passing type is json we can chang it if we needed*/
    public ResponseEntity<StandardResponse> createDoctor(@RequestBody RequestUserDto userDto ){
        UserService.signup(userDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"user saved",userDto.getFullname()),
                HttpStatus.CREATED
        );

    }
}
