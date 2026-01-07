package com.ivangeorgiev.domuapi.data.request.person;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePersonRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private Integer age;

    @NotNull(message = "Uses elevator flag cannot be null")
    private Boolean usesElevator;
}
