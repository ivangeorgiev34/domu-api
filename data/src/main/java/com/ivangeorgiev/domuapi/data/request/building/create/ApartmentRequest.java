package com.ivangeorgiev.domuapi.data.request.building.create;

import jakarta.validation.Valid;import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;import lombok.Getter;import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApartmentRequest {

    @Positive(message = "Area must be a positive number")
    private BigDecimal area;

    @PositiveOrZero(message = "Pets count must be positive or zero")
    private Integer petsCount;
}

