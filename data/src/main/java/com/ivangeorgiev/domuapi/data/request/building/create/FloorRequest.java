package com.ivangeorgiev.domuapi.data.request.building.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;import lombok.Getter;import lombok.Setter;import java.util.List;

@Getter
@Setter
public class FloorRequest {

    @Valid
    @NotEmpty(message = "Floors should have apartments")
    private List<ApartmentRequest> apartments;
}
