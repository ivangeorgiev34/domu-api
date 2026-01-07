package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.PersonService;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import com.ivangeorgiev.domuapi.data.request.person.CreatePersonRequest;
import com.ivangeorgiev.domuapi.data.request.person.EditPersonRequest;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final PersonService personService;

    public OwnerController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PersonDto>> create(@RequestParam("apartmentId") UUID apartmentId, @Valid @RequestBody CreatePersonRequest request){

       PersonDto owner = this.personService.assignOwner(apartmentId, request);

       return ApiResponseFactory.create("Owner created", HttpStatus.CREATED, owner);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<PersonDto>> edit(@RequestParam("id") UUID id, @Valid @RequestBody EditPersonRequest request){

        if(request.getAge() < 18)
            return ApiResponseFactory.create("Owner must be at least 18 years old", HttpStatus.BAD_REQUEST);

        PersonDto person = this.personService.edit(id, request);

        return ApiResponseFactory.create("Edited owner", HttpStatus.OK, person);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("id") UUID id){

        this.personService.delete(id);

        return ApiResponseFactory.create("Deleted owner", HttpStatus.OK);
    }
}
