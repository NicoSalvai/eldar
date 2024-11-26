package com.salvai.eldar.services;

import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.api.CreditCardRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreditCardService {
    CreditCardDto addCreditCard(CreditCardRequest creditCardRequest);

    Page<CreditCardDto> getCreditCards(Pageable pageable);

    CreditCardDto getCreditCardById(Integer creditCardId);

    void deleteCreditCardById(Integer creditCardId);
}
