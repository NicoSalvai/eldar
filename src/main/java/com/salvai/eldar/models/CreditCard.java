package com.salvai.eldar.models;

import com.salvai.eldar.models.enums.CreditCardBrand;

import java.time.LocalDate;

public record CreditCard(CreditCardBrand brand, String number, Person person, String fullName, LocalDate expirationDate){

    public boolean isValid() {
        return expirationDate.isAfter(LocalDate.now());
    }
}
