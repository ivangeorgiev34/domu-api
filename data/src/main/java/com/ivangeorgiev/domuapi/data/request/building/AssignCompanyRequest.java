package com.ivangeorgiev.domuapi.data.request.building;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssignCompanyRequest {

    @NotNull(message = "Company id cannot be blank")
    private UUID companyId;
}
