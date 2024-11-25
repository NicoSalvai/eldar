package com.salvai.eldar.services;

import com.salvai.eldar.models.CreditCard;
import com.salvai.eldar.models.Person;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.exceptions.ValidationException;
import com.salvai.eldar.services.validators.CreditCardDataValidator;
import com.salvai.eldar.services.validators.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditCardService {
    private List<CreditCard> creditCards = new ArrayList<>();

    public void registerCreditCard(String brand, String number, String fullName, Person person, String expirationDate) throws ValidationException {

        final var creditCardDataValidator = new CreditCardDataValidator(brand, number, fullName, expirationDate);
        if(!creditCardDataValidator.validate()){
            throw new ValidationException("Hubo un problema al registrar la tarjeta, revise los siguientes campos:",
                    creditCardDataValidator.getErrors());
        }

        final var brandEnum = CreditCardBrand.valueOf(brand);
        final var expirationDateAsDate = LocalDate.parse(expirationDate, Validator.DATE_TIME_FORMATTER);

        if(creditCards.stream().filter(CreditCard::isValid)
            .anyMatch(card -> card.brand().equals(brandEnum) && card.number().equals(number))){
            throw new ValidationException("La tarjeta que se quiere registrar ya existe.");
        }

        creditCards.add(new CreditCard(brandEnum, number, person, fullName, expirationDateAsDate));
    }
}
