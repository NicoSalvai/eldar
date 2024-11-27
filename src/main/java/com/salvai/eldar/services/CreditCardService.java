package com.salvai.eldar.services;

import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.api.CreditCardRequest;
import com.salvai.eldar.models.jpa.CreditCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreditCardService {
    CreditCardDto addCreditCard(CreditCardRequest creditCardRequest);

    Page<CreditCardDto> getCreditCards(Pageable pageable);

    CreditCardDto getCreditCardById(Integer creditCardId);

    CreditCardEntity getCreditCardEntityById(Integer creditCardId);

    void deleteCreditCardById(Integer creditCardId);

    String maskCreditCardNumber(String creditCardNumber);
}
