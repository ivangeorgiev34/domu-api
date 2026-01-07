package com.ivangeorgiev.domuapi.data.request.building.create;

import jakarta.validation.Valid;import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateBuildingRequest {

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Positive(message = "Area must be a positive number")
    private BigDecimal area;

    @Valid
    @NotEmpty(message = "Buildings should have floors")
    private List<FloorRequest> floors;
}
