package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.ApartmentService;
import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApartmentDto>>> getAll(@RequestParam("buildingId") UUID buildingId){

        List<ApartmentDto> apartments = this.apartmentService.getAll(buildingId);

        return ApiResponseFactory.create("Apartments retrieved", HttpStatus.OK, apartments);
    }
}
