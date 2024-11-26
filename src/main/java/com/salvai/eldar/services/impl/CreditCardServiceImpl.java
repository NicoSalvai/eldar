package com.salvai.eldar.services.impl;

import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.api.CreditCardRequest;
import com.salvai.eldar.models.jpa.CreditCardEntity;
import com.salvai.eldar.repositories.CreditCardRepository;
import com.salvai.eldar.services.CreditCardService;
import com.salvai.eldar.services.CreditCardValidatorService;
import com.salvai.eldar.services.UserService;
import com.salvai.eldar.transformers.CreditCardentityToDtoTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    @NonNull
    private final CreditCardRepository creditCardRepository;

    @NonNull
    private final UserService userService;

    @NonNull
    private final CreditCardValidatorService creditCardValidatorService;

    @NonNull
    private final CreditCardentityToDtoTransformer creditCardentityToDtoTransformer;

    @Override
    public CreditCardDto addCreditCard(CreditCardRequest creditCardRequest) {
        if(creditCardRepository.findByBrandAndNumberAndExpirationDateAfter(
            creditCardRequest.brand(), creditCardRequest.number(), LocalDate.now()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Credit Card with that number and brand already exists");
        }

        creditCardValidatorService.validateCreditCardNumber(creditCardRequest.number());

        final var creditCardEntity = new CreditCardEntity();
        creditCardEntity.setBrand(creditCardRequest.brand());
        creditCardEntity.setNumber(creditCardRequest.number());
        creditCardEntity.setUserEntity(userService.getUserEntityById(creditCardRequest.userId()));
        creditCardEntity.setExpirationDate(creditCardRequest.expirationDate());
        creditCardEntity.setCvv(generateCVV());

        final var createdCreditCard = creditCardRepository.save(creditCardEntity);

        // TODO Implement email sending to user;

        return creditCardentityToDtoTransformer.convert(createdCreditCard);
    }

    @Override
    public Page<CreditCardDto> getCreditCards(Pageable pageable) {
        return creditCardRepository.findAll(pageable).map(creditCardentityToDtoTransformer::convert);
    }

    @Override
    public CreditCardDto getCreditCardById(Integer creditCardId) {
        return creditCardentityToDtoTransformer.convert(getCreditCardEntityById(creditCardId));
    }

    protected CreditCardEntity getCreditCardEntityById(Integer creditCardId){
        return creditCardRepository.findById(creditCardId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
    }

    @Override
    public void deleteCreditCardById(Integer creditCardId) {
        final var creditCardEntity = getCreditCardEntityById(creditCardId);
        creditCardRepository.delete(creditCardEntity);
    }

    private String generateCVV() {
        Random random = new Random();
        int cvvNumber = random.nextInt(1000);
        return String.format("%03d", cvvNumber);
    }

}
