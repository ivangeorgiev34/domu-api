package com.ivangeorgiev.domuapi.data.dtos.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.dtos.company.CompanyDto;
import com.ivangeorgiev.domuapi.data.models.Building;
import com.ivangeorgiev.domuapi.data.models.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private List<BuildingDto> buildings;

    private CompanyDto company;
}
