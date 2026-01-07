package com.ivangeorgiev.domuapi.core.services;

import com.ivangeorgiev.domuapi.core.exceptions.NotFoundException;
import com.ivangeorgiev.domuapi.core.mappers.EmployeeMapper;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import com.ivangeorgiev.domuapi.data.models.Building;
import com.ivangeorgiev.domuapi.data.models.Company;
import com.ivangeorgiev.domuapi.data.models.Employee;
import com.ivangeorgiev.domuapi.data.repositories.BuildingRepository;
import com.ivangeorgiev.domuapi.data.repositories.CompanyRepository;
import com.ivangeorgiev.domuapi.data.repositories.EmployeeRepository;
import com.ivangeorgiev.domuapi.data.request.employee.get.GetAllEmployeeRequestParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final BuildingRepository buildingRepository;

    public EmployeeService (EmployeeRepository employeeRepository,
                            CompanyRepository companyRepository,
                            BuildingRepository buildingRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.buildingRepository = buildingRepository;
    }

    @Transactional
    public EmployeeDto createEmployee(String firstName, String lastName, UUID companyId) {
        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company doesn't exist"));

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .company(company)
                .build();

        Employee savedEmployee = this.employeeRepository.save(employee);

        return EmployeeMapper.toEmployeeDto(savedEmployee);
    }

    public Boolean existsEmployee(UUID employeeId) {
        return this.employeeRepository.existsById(employeeId);
    }

    @Transactional
    public void deleteEmployee(UUID employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee doesn't exist"));

        Employee employeeWithLeastBuildings = this.employeeRepository
                .findFirstByOrderByBuildingsCountAscNative(employeeId, employee.getCompany().getId());

        if(employeeWithLeastBuildings == null){
            this.employeeRepository.delete(employee);
            return;
        }

        for (Building building : employee.getBuildings()) {
            building.setEmployee(employeeWithLeastBuildings);
            buildingRepository.save(building);
        }

        this.employeeRepository.delete(employee);
    }

    @Transactional
    public EmployeeDto editEmployee(UUID id, String firstName, String lastName) {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee doesn't exist"));

        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        this.employeeRepository.save(employee);

        return EmployeeMapper.toEmployeeDto(employee);
    }

    public List<EmployeeDto> getAll(GetAllEmployeeRequestParams params){

        List<Employee> employees = this.employeeRepository.findAllBYSortAndFilter(params.getCompanyId(), params.getSortBy(), params.getName());

        return employees.stream().map(EmployeeMapper::toEmployeeDto).toList();
    }
}
