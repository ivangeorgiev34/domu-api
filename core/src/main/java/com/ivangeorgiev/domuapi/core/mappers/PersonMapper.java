package com.ivangeorgiev.domuapi.core.mappers;

import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import com.ivangeorgiev.domuapi.data.models.Person;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonMapper {

    private PersonMapper() {}

    public static PersonDto toPersonDto(Person person) {

        if(person == null) return null;

        return PersonDto.builder()
                .id(person.getId())
                .age(person.getAge())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .usesElevator(person.getUsesElevator())
                .build();
    }
}
