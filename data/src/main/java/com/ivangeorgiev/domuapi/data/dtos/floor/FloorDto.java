package com.ivangeorgiev.domuapi.data.dtos.floor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivangeorgiev.domuapi.data.dtos.apartment.ApartmentDto;
import com.ivangeorgiev.domuapi.data.dtos.building.BuildingDto;
import com.ivangeorgiev.domuapi.data.models.Apartment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FloorDto {

    private UUID id;

    private Integer number;

    private BuildingDto building;

    private List<ApartmentDto> apartments;
}
