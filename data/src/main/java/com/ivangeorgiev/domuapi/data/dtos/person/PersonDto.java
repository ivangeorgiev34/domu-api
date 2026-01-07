package com.ivangeorgiev.domuapi.data.dtos.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private Integer age;

    private Boolean usesElevator;

    private List<ApartmentDto> ownedApartments;

    private List<ApartmentDto> residentApartments;
}
