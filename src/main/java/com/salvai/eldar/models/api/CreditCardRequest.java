package com.salvai.eldar.models.api;

import com.salvai.eldar.models.enums.CreditCardBrand;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreditCardRequest(
    @NotNull(message = "brand is required")
    CreditCardBrand brand,

    @NotBlank(message = "card number is required")
    @Pattern(regexp = "^\\d{13,16}$", message = "card number should be a number with 13 to 16 digits")
    String number,

    @NotNull(message = "expiration date is required")
    @Future(message = "expiration date should be after today")
    LocalDate expirationDate,

    @NotNull(message = "userId is required")
    Integer userId){
}
