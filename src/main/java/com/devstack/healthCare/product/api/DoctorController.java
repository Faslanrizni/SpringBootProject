package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestDoctorDto;
import com.devstack.healthCare.product.service.DoctorService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController/*The class is marked as @RestController, meaning it is ready to handle web requests*/
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
    public ResponseEntity<StandardResponse> deleteDoctor(@PathVariable long id){
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(
                new StandardResponse(204,"deleted data",id),
                HttpStatus.NO_CONTENT
        );
    }
    @PutMapping(params = "id")
    public ResponseEntity<StandardResponse> updateDoctor(
            @RequestParam long id,
            @RequestBody RequestDoctorDto doctorDto){
        doctorService.updateDoctor(id,doctorDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"update data",doctorDto.getName()),
                HttpStatus.CREATED
        );
    }
    @GetMapping(value = "/list",params = {"searchText","page","size"})
    public ResponseEntity<StandardResponse> findAllDoctor(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ){

        return new ResponseEntity<>(
                new StandardResponse(200,"data list",doctorService.getAllDoctors(
                        searchText, page, size)),
                HttpStatus.OK
        );
    }

}
