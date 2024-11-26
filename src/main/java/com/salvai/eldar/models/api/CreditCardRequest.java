package com.salvai.eldar.models.api;

import com.salvai.eldar.models.enums.CreditCardBrand;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreditCardRequest(
    @NotNull(message = "brand is required")
    CreditCardBrand brand,

    @NotBlank(message = "card number is required")
    String number,

    @NotNull(message = "expiration date is required")
    @Future(message = "expiration date should be after today")
    LocalDate expirationDate,

    @NotNull(message = "userId is required")
    Integer userId){
}
