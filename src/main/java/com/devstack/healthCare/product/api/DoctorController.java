package com.devstack.healthCare.product.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController {
    @PostMapping
    public String createDoctor(){
        return "createDoctor";
    }
    @GetMapping
    public String findDoctor(){
        return "findDoctor";
    }
    @DeleteMapping
    public String deleteDoctor(){
        return "deleteDoctor";
    }
    @PutMapping
    public String updateDoctor(){
        return "updateDoctor";
    }
    @GetMapping("/list")
    public String findAllDoctor(){
        return "findAllDoctor";
    }

}
