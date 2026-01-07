package com.ivangeorgiev.domuapi.data.dtos.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyDto {

    private UUID id;

    private String name;

    private BigDecimal petTax;

    private BigDecimal taxPerResident;

    private BigDecimal taxPerSquareMeter;

    private BigDecimal income;

    private List<EmployeeDto> employees;

    private List<BuildingDto> buildings;
}
