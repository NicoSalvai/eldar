package com.salvai.eldar.models.api;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequest(
    @NotEmpty(message = "firstName is required")
    @Size(max = 50, message = "firstName should be shorter than 50 characters")
    String firstName,

    @NotEmpty(message = "lastName is required")
    @Size(max = 50, message = "lastname should be shorter than 50 characters")
    String lastName,

    @NotNull(message = "dni is required")
    Integer dni,

    @NotNull(message = "birthDate is required")
    @Past(message = "birthDate must be before today")
    LocalDate birthDate,

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email")
    String email) {
}
