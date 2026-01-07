package com.ivangeorgiev.domuapi.data.request.employee.get;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetAllEmployeeRequestParams {
    @NotNull(message = "Company id cannot be null")
    private UUID companyId;

    private String sortBy = "";

    private String name = "";
}
