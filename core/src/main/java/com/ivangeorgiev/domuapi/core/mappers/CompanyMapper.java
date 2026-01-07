package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.dtos.company.CompanyDto;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import com.ivangeorgiev.domuapi.data.models.Company;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CompanyMapper {

    private CompanyMapper() {}

    public static CompanyDto toCompanyDto(Company company) {

        if(company == null) return null;

        return CompanyDto
                .builder()
                .id(company.getId())
                .name(company.getName())
                .petTax(company.getPetTax())
                .taxPerResident(company.getTaxPerResident())
                .taxPerSquareMeter(company.getTaxPerSquareMeter())
                .income(company.getIncome())
                .build();
    }
}
