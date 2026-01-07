package com.ivangeorgiev.domuapi.data.dtos.apartment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.floor.FloorDto;
import com.ivangeorgiev.domuapi.data.dtos.person.PersonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApartmentDto {

    private UUID id;

    private Integer number;

    private BigDecimal area;

    private Integer petsCount;

    private PersonDto owner;

    private List<PersonDto> residents;

    private FloorDto floor;
}
