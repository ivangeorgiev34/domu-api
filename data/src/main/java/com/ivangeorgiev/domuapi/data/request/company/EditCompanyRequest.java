package com.ivangeorgiev.domuapi.data.request.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditCompanyRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Positive(message = "Pet tax must be bigger than 0")
    private BigDecimal petTax;

    @Positive(message = "Tax per resident must be bigger than 0")
    private BigDecimal taxPerResident;

    @Positive(message = "Tax per square meter must be bigger than 0")
    private BigDecimal taxPerSquareMeter;
}
