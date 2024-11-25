package com.salvai.eldar.services.validators;

import com.salvai.eldar.models.enums.CreditCardBrand;

import java.time.LocalDate;
import java.util.Locale;

public class CreditCardDataValidator extends Validator {

    private static final String BRAND = "Marca";
    private static final String CARD_NUMBER = "Numero de tarjeta";
    private static final String EXPIRATION_DATE = "Fecha de Vencimiento";
    private static final String FULL_NAME = "Nombre Completo";
    public static final int BRAND_MAX_LENGTH = 10;
    public static final int CARD_NUMBER_MIN_LENGTH = 13;
    public static final int CARD_NUMBER_MAX_LENGTH = 16;
    public static final int FULL_NAME_MAX_LENGTH = 255;

    private final String brand;
    private final String cardNumber;
    private final String expirationDate;
    private final String fullName;

    public CreditCardDataValidator(String brand, String cardNumber, String fullName, String expirationDate) {
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.fullName = fullName;
    }

    public boolean validate(){
        validateBrand(brand);
        validateCardNumber(cardNumber);
        validateExpirationDate(expirationDate);
        validateFullName(fullName);
        return errors.isEmpty();
    }

    private void validateBrand(String brand){
        notEmpty(brand, BRAND);
        shorterOrEqualTo(brand, BRAND_MAX_LENGTH, BRAND);
        try {
            CreditCardBrand.valueOf(brand.toUpperCase());
        } catch (IllegalArgumentException ex){
            errors.add("%s (%s) is not a valid option".formatted(BRAND, brand));
        }
    }

    private void validateCardNumber(String cardNumber){
        notEmpty(cardNumber, CARD_NUMBER);
        isNumber(cardNumber, CARD_NUMBER);
        shorterOrEqualTo(cardNumber, CARD_NUMBER_MAX_LENGTH, CARD_NUMBER);
        longerOrEqualTo(cardNumber, CARD_NUMBER_MIN_LENGTH, CARD_NUMBER);
        luhnCheck(cardNumber);
    }

    private void validateExpirationDate(String expirationDate){
        notEmpty(expirationDate, EXPIRATION_DATE);
        shorterOrEqualTo(expirationDate, DATE_TIME_FORMAT.length(), EXPIRATION_DATE);
        isDate(expirationDate, EXPIRATION_DATE);
        dateAfter(expirationDate, LocalDate.now(), EXPIRATION_DATE);
    }


    private void validateFullName(String name){
        notEmpty(name, FULL_NAME);
        shorterOrEqualTo(name, FULL_NAME_MAX_LENGTH, FULL_NAME);
    }

    private void luhnCheck(String cardNumber) {
        int total = 0;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            total += (cardNumber.length() - i) % 2 == 0 ? evenDigitLuhnModification(digit) : digit;
        }

        if (total % 10 != 0) {
            errors.add("%s no es un numero de tarjeta valido".formatted(CARD_NUMBER));
        }
    }

    private int evenDigitLuhnModification(int number){
        number *= 2;
        if (number > 9) {
            number -= 9;
        }
        return number;
    }
}
