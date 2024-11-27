package com.salvai.eldar.models.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OperationRequest(
    @NotNull(message = "amount is required")
    @Max(value = 10_000, message = "An operation above 10.000 is not allowed")
    double amount,

    @NotNull(message = "detail is required")
    String detail,

    @NotBlank(message = "cvv is required")
    String cvv) {
}
