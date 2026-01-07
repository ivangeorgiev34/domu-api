package com.ivangeorgiev.domuapi.data.dtos.building;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.company.CompanyDto;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import com.ivangeorgiev.domuapi.data.dtos.floor.FloorDto;
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
public class BuildingDto {

    private UUID id;

    private String address;

    private BigDecimal area;

    private List<FloorDto> floors;

    private EmployeeDto employee;

    private CompanyDto company;
}
