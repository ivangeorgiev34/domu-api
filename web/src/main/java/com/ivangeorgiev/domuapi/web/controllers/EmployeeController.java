package com.ivangeorgiev.domuapi.web.controllers;

import com.ivangeorgiev.domuapi.core.services.CompanyService;
import com.ivangeorgiev.domuapi.core.services.EmployeeService;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import com.ivangeorgiev.domuapi.data.models.Employee;
import com.ivangeorgiev.domuapi.data.request.employee.CreateEmployeeRequest;
import com.ivangeorgiev.domuapi.data.request.employee.EditEmployeeRequest;
import com.ivangeorgiev.domuapi.data.request.employee.get.GetAllEmployeeRequestParams;
import com.ivangeorgiev.domuapi.web.utils.ApiResponse;
import com.ivangeorgiev.domuapi.web.utils.ApiResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {

        if(!companyService.existsCompany(request.getCompanyId()))
            return ApiResponseFactory.create("Company doesn't exist", HttpStatus.BAD_REQUEST);

        EmployeeDto employee = employeeService.createEmployee(request.getFirstName(), request.getLastName(), request.getCompanyId());

        return ApiResponseFactory.create("Employee created", HttpStatus.CREATED, employee);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@RequestParam(name = "id") UUID id) {

        if(!employeeService.existsEmployee(id))
            return ApiResponseFactory.create("Employee doesn't exist", HttpStatus.BAD_REQUEST);

        employeeService.deleteEmployee(id);

        return ApiResponseFactory.create("Employee deleted", HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> editEmployee(@RequestParam(name = "id") UUID id, @Valid @RequestBody EditEmployeeRequest request) {

        EmployeeDto employee = employeeService.editEmployee(id, request.getFirstName(), request.getLastName());

        return ApiResponseFactory.create("Employee edited", HttpStatus.OK, employee);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAll(@Valid GetAllEmployeeRequestParams params){

        List<String> validSortByValues = List.of("name", "buildingsCount");

        if(validSortByValues.stream().noneMatch(x -> x.equalsIgnoreCase(params.getSortBy())))
            return ApiResponseFactory.create("Invalid sortBy param, must be 'name' or 'buildingsCount'", HttpStatus.BAD_REQUEST);

        List<EmployeeDto> employees = this.employeeService.getAll(params);

        return ApiResponseFactory.create("Employee retrieved", HttpStatus.OK, employees);
    }
}
