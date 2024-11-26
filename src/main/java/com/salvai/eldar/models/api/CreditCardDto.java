package com.salvai.eldar.models.api;

import com.salvai.eldar.models.enums.CreditCardBrand;

import java.time.LocalDate;

public record CreditCardDto(
    Integer id,
    CreditCardBrand brand,
    String number,
    LocalDate expirationDate,
    Integer userId,
    String fullName,
    Boolean isValid) {
}
