package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.service.DoctorService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctors")

/* api => application program interface
doctores wala s akura ona*/

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping/*default data passing type is json we can chang it if we needed*/
    public ResponseEntity<StandardResponse> createDoctor(@RequestBody RequestDoctorDto doctorDto){
        doctorService.createDoctor(doctorDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"Doctor saved",doctorDto.getName()),
                HttpStatus.CREATED
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> findDoctor(@PathVariable long id){
        return new ResponseEntity<>(
                new StandardResponse(200,"Doctor data",doctorService.getDoctor(id)),
                HttpStatus.OK
        );
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
