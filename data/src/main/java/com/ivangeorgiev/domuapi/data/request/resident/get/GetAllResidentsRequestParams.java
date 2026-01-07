package com.ivangeorgiev.domuapi.data.request.resident.get;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetAllResidentsRequestParams {

    @NotNull(message = "Building cannot be null")
    private UUID buildingId;

    private String sortBy = "";

    private String name = "";

    private Integer age = 0;
}
