package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.dtos.floor.FloorDto;
import com.ivangeorgiev.domuapi.data.models.Building;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BuildingMapper {

    private BuildingMapper() {}

    public static BuildingDto toBuildingDto(Building building) {

        if(building == null) return null;

        List<FloorDto> floors = Optional.ofNullable(building.getFloors())
                .orElse(Collections.emptyList())
                .stream()
                .map(FloorMapper::toFloorDto)
                .toList();

        return BuildingDto.builder()
                .id(building.getId())
                .area(building.getArea())
                .address(building.getAddress())
                .employee(EmployeeMapper.toEmployeeDto(building.getEmployee()))
                .company(CompanyMapper.toCompanyDto(building.getCompany()))
                .floors(floors)
                .build();
    }
}
