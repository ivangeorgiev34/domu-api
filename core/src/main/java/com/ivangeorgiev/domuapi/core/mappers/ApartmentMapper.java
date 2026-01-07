package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import com.ivangeorgiev.domuapi.data.models.Apartment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ApartmentMapper {

    private ApartmentMapper() {}

    public static ApartmentDto toApartmentDto(Apartment apartment) {

        if(apartment == null) return null;

        List<PersonDto> residents = Optional.ofNullable(apartment.getResidents())
                .orElse(Collections.emptyList())
                .stream()
                .map(PersonMapper::toPersonDto)
                .toList();

        return ApartmentDto.builder()
                .id(apartment.getId())
                .area(apartment.getArea())
                .petsCount(apartment.getPetsCount())
                .number(apartment.getNumber())
                .owner(PersonMapper.toPersonDto(apartment.getOwner()))
                .residents(residents)
                .build();
    }
}
