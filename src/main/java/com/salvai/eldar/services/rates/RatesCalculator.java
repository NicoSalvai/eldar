package com.salvai.eldar.services.rates;

import com.salvai.eldar.models.enums.CreditCardBrand;

import java.time.LocalDate;

@FunctionalInterface
public interface RatesCalculator {

    double calculateRate(LocalDate date);

    static RatesCalculator getRatesCalculator(CreditCardBrand creditCardBrand){
        return switch (creditCardBrand){
            case VISA -> (LocalDate date) -> date.getYear() / (double) date.getMonthValue();
            case NARA -> (LocalDate date) -> date.getDayOfMonth() * 0.5;
            case AMEX -> (LocalDate date) -> date.getMonthValue() * 0.1;
        };
    }
}