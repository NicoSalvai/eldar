package com.salvai.eldar.services;

import com.salvai.eldar.models.CreditCard;
import com.salvai.eldar.models.CreditCardRate;
import com.salvai.eldar.models.Person;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.exceptions.ValidationException;
import com.salvai.eldar.services.rates.RatesCalculator;
import com.salvai.eldar.services.validators.CreditCardDataValidator;
import com.salvai.eldar.services.validators.DateValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.salvai.eldar.services.validators.Validator.DATE_TIME_FORMATTER;

public class CreditCardService {
    private final List<CreditCard> creditCards = new ArrayList<>();

    public void registerCreditCard(String brand, String number, String fullName, Person person, String expirationDate) throws ValidationException {

        final var creditCardDataValidator = new CreditCardDataValidator(brand, number, fullName, expirationDate);
        if (creditCardDataValidator.isValid()) {
            throw new ValidationException("Hubo un problema al registrar la tarjeta, revise los siguientes campos:",
                    creditCardDataValidator.getErrors());
        }

        final var brandEnum = CreditCardBrand.valueOf(brand.toUpperCase());
        final var expirationDateAsDate = LocalDate.parse(expirationDate, DATE_TIME_FORMATTER);

        if (creditCards.stream().filter(CreditCard::isValid)
            .anyMatch(card -> card.brand().equals(brandEnum) && card.number().equals(number))) {
            throw new ValidationException("La tarjeta que se quiere registrar ya existe.");
        }

        creditCards.add(new CreditCard(brandEnum, number, person, fullName, expirationDateAsDate));
    }

    public List<CreditCard> getCreditCardsByDni(String dni) throws ValidationException {
        if (dni == null) {
            throw new ValidationException("El dni ingresado no deberia ser nulo");
        }
        return creditCards.stream().filter(creditCard -> creditCard.person().dni().equals(dni)).toList();
    }

    public List<CreditCardRate> getCreditCardBrandsRateByDate(String date) throws ValidationException {
        LocalDate dateValue;
        if (date == null || date.isBlank()) {
            dateValue = LocalDate.now();
        } else {
            final var dateValidator = new DateValidator(date);
            if (dateValidator.isValid()) {
                throw new ValidationException("Hubo un problema con la fecha ingresada:", dateValidator.getErrors());
            }
            dateValue = LocalDate.parse(date, DATE_TIME_FORMATTER);
        }

        return Arrays.stream(CreditCardBrand.values())
                .map(brand -> new CreditCardRate(brand, RatesCalculator.getRatesCalculator(brand).calculateRate(dateValue)))
                .toList();
    }
}
