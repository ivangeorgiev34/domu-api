package com.ivangeorgiev.domuapi.data.request.building.edit;

import com.ivangeorgiev.domuapi.data.request.building.create.FloorRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditBuildingRequest {

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Positive(message = "Area must be a positive number")
    private BigDecimal area;
}
