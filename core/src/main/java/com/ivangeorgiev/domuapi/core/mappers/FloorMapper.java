package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.dtos.floor.FloorDto;
import com.ivangeorgiev.domuapi.data.models.Floor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FloorMapper {

    private FloorMapper() {}

    public static FloorDto toFloorDto(Floor floor) {

        if(floor == null) return null;

        List<ApartmentDto> apartments = Optional.ofNullable(floor.getApartments())
                .orElse(Collections.emptyList())
                .stream()
                .map(ApartmentMapper::toApartmentDto)
                .toList();

        return FloorDto.builder()
                .id(floor.getId())
                .number(floor.getNumber())
                .apartments(apartments)
                .build();
    }
}
