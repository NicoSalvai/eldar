package com.salvai.eldar.services.impl;

import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.OperationRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.jpa.OperationEntity;
import com.salvai.eldar.repositories.OperationRepository;
import com.salvai.eldar.services.CreditCardService;
import com.salvai.eldar.services.EmailService;
import com.salvai.eldar.services.OperationService;
import com.salvai.eldar.services.RateService;
import com.salvai.eldar.services.rates.RatesCalculator;
import com.salvai.eldar.transformers.OperationEntityToDtoTransformer;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private static final String NEW_OPERATION_SUBJECT = "New operation detected on your Credit Card";
    private static final String NEW_OPERATION_CONTENT = """
        There was an operation detected on your %s credit card:
        PAN: %s
        Amount: %s
        Interest rate: %s
        Total amount: %s
        Detail: %s""";

    @NonNull
    private final OperationRepository operationRepository;

    @NonNull
    private final OperationEntityToDtoTransformer operationEntityToDtoTransformer;

    @NonNull
    private final CreditCardService creditCardService;

    @NonNull
    private final RateService rateService;

    @NonNull
    private final EmailService emailService;

    @Override
    @Transactional
    public OperationDto addOperation(Integer creditCardId, OperationRequest operationRequest) {

        final var creditCardEntity = creditCardService.getCreditCardEntityById(creditCardId);
        if(!creditCardEntity.getCvv().equals(operationRequest.cvv())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The CVV is not valid");
        }

        final var operationRateDto = rateService.getOperationRate(creditCardEntity.getBrand(), operationRequest.amount());

        final var operationEntity = new OperationEntity();
        operationEntity.setAmount(operationRequest.amount());
        operationEntity.setDetail(operationRequest.detail());
        operationEntity.setTotal(operationRateDto.finalAmount());
        operationEntity.setInterestRate(operationRateDto.rate());
        operationEntity.setCreditCardEntity(creditCardEntity);

        final var createdOperationEntity = operationRepository.save(operationEntity);

        final var content = NEW_OPERATION_CONTENT.formatted(creditCardEntity.getBrand(),
            creditCardService.maskCreditCardNumber(creditCardEntity.getNumber()),
            createdOperationEntity.getAmount(),
            createdOperationEntity.getInterestRate(),
            createdOperationEntity.getTotal(),
            createdOperationEntity.getDetail());

        emailService.sendEmail(creditCardEntity.getUserEntity().getEmail(), NEW_OPERATION_SUBJECT, content);

        return operationEntityToDtoTransformer.convert(createdOperationEntity);
    }

    @Override
    public OperationDto getOperation(Integer operationId) {
        return operationEntityToDtoTransformer.convert(getOperationEntity(operationId));
    }

    protected OperationEntity getOperationEntity(Integer operationId) {
        return operationRepository.findById(operationId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found"));
    }
}
