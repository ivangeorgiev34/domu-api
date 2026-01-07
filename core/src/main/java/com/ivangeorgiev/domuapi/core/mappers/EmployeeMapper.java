package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.dtos.employee.EmployeeDto;
import com.ivangeorgiev.domuapi.data.dtos.floor.FloorDto;
import com.ivangeorgiev.domuapi.data.models.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeMapper {

    private EmployeeMapper() {}

    public static EmployeeDto toEmployeeDto(Employee employee) {

        if(employee == null) return null;

        return EmployeeDto
                .builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .build();
    }
}
