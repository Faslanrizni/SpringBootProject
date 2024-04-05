package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctors")

/* api => application program interface
doctores wala s akura ona*/

public class DoctorController {
    @PostMapping/*default data passing type is json we can chang it if we needed*/
    public String createDoctor(@RequestBody RequestDoctorDto doctorDto){
        return doctorDto.toString();
    }
    @GetMapping("/{id}")
    public String findDoctor(@PathVariable String id){
        return id+"";
    }
    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable String id){
        return id+"";
    }
    @PutMapping(params = "id")
    public String updateDoctor(
            @RequestParam String id,
            @RequestBody RequestDoctorDto doctorDto){
        return doctorDto.toString();
    }
    @GetMapping(value = "/list",params = {"searchText","page","size"})
    public String findAllDoctor(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ){

        return "findAllDoctor";
    }

}
