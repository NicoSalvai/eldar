package com.salvai.eldar.services.impl;

import com.salvai.eldar.services.CreditCardValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreditCardValidatorServiceImpl implements CreditCardValidatorService {

    @Override
    public void validateCreditCardNumber(String creditCardNumber) {
         int total = 0;
            for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
                int digit = Integer.parseInt(creditCardNumber.substring(i, i + 1));
                total += (creditCardNumber.length() - i) % 2 == 0 ? evenDigitLuhnModification(digit) : digit;
            }

            if (total % 10 != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "%s is not a valid credit card number".formatted(creditCardNumber));
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
