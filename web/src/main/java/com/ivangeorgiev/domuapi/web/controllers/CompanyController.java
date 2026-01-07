package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.CompanyService;
import com.ivangeorgiev.domuapi.data.dtos.company.CompanyDto;
import com.ivangeorgiev.domuapi.data.request.company.CreateCompanyRequest;
import com.ivangeorgiev.domuapi.data.request.company.EditCompanyRequest;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyDto>> createCompany(@Valid @RequestBody CreateCompanyRequest request){

        CompanyDto company = this.companyService.createCompany(request);

        return ApiResponseFactory.create("Company created", HttpStatus.CREATED, company);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@RequestParam(name = "id") UUID id){

        if(!companyService.existsCompany(id))
            return ApiResponseFactory.create("Company does not exist", HttpStatus.NOT_FOUND);

        companyService.deleteCompany(id);

        return ApiResponseFactory.create("Company deleted", HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<CompanyDto>> editCompany (@RequestParam(name = "id") UUID id, @Valid @RequestBody EditCompanyRequest request){

        CompanyDto companyDto = this.companyService.editCompany(id, request);

        return ApiResponseFactory.create("Company edited", HttpStatus.OK, companyDto);
    }

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<CompanyDto>> pay(@RequestParam("id") UUID id, @RequestParam("apartmentId") UUID apartmentId){

        CompanyDto company = this.companyService.pay(id, apartmentId);

        return ApiResponseFactory.create("Paid company",  HttpStatus.OK, company);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getAll(){

        List<CompanyDto> companies = this.companyService.getAll();

        return ApiResponseFactory.create("Companies retrieved", HttpStatus.OK, companies);
    }
}
