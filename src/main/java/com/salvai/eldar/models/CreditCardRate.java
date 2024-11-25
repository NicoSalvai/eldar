package com.salvai.eldar.models;

import com.salvai.eldar.models.enums.CreditCardBrand;

public record CreditCardRate(CreditCardBrand brand, double rate) {
}
