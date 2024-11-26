package com.salvai.eldar.controllers.impl;

import com.salvai.eldar.controllers.CreditCardController;
import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.api.CreditCardRequest;
import com.salvai.eldar.services.CreditCardService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CreditCardControllerImpl implements CreditCardController {

    @NotNull
    private final CreditCardService creditCardService;


    @Override
    public ResponseEntity<Void> addCreditCard(CreditCardRequest creditCardRequest) {
        final var creditCard = creditCardService.addCreditCard(creditCardRequest);

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creditCard.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Page<CreditCardDto>> getCreditCards(Pageable pageable) {
        return ResponseEntity.ok(creditCardService.getCreditCards(pageable));
    }

    @Override
    public ResponseEntity<CreditCardDto> getCreditCardById(Integer creditCardId) {
        return ResponseEntity.ok(creditCardService.getCreditCardById(creditCardId));
    }

    @Override
    public ResponseEntity<Void> deleteCreditCardById(Integer creditCardId) {
        creditCardService.deleteCreditCardById(creditCardId);
        return ResponseEntity.noContent().build();
    }
}
