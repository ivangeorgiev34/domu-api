package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.PersonService;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import com.ivangeorgiev.domuapi.data.request.person.CreatePersonRequest;
import com.ivangeorgiev.domuapi.data.request.person.EditPersonRequest;
import com.ivangeorgiev.domuapi.data.request.resident.get.GetAllResidentsRequestParams;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    private final PersonService personService;

    public ResidentController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PersonDto>> create(@RequestParam("apartmentId") UUID apartmentId, @Valid @RequestBody CreatePersonRequest request) {

        PersonDto person = this.personService.assignResident(apartmentId, request);

        return ApiResponseFactory.create("Resident created", HttpStatus.CREATED, person);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<PersonDto>> edit(@RequestParam("id") UUID id, @Valid @RequestBody EditPersonRequest request){

        if(request.getAge() <= 0)
            return ApiResponseFactory.create("Age must be greater than 0", HttpStatus.BAD_REQUEST);

        PersonDto person = this.personService.edit(id, request);

        return ApiResponseFactory.create("Edited resident", HttpStatus.OK, person);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("id") UUID id){

        this.personService.delete(id);

        return ApiResponseFactory.create("Deleted resident", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonDto>>> getAll(@Valid GetAllResidentsRequestParams params){

        List<String> validSortByValues = List.of("name", "age");

        if(validSortByValues.stream().noneMatch(x -> x.equalsIgnoreCase(params.getSortBy())))
            return ApiResponseFactory.create("Invalid sortBy param, must be 'name' or 'age'", HttpStatus.BAD_REQUEST);

        List<PersonDto> people = this.personService.getAll(params);
        return ApiResponseFactory.create("People retrieved", HttpStatus.OK, people);
    }
}
