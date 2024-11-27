package com.salvai.eldar.services.impl;

import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.OperationRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.jpa.CreditCardEntity;
import com.salvai.eldar.models.jpa.OperationEntity;
import com.salvai.eldar.models.jpa.UserEntity;
import com.salvai.eldar.repositories.OperationRepository;
import com.salvai.eldar.services.CreditCardService;
import com.salvai.eldar.services.EmailService;
import com.salvai.eldar.services.RateService;
import com.salvai.eldar.transformers.OperationEntityToDtoTransformer;
import jdk.dynalink.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationEntityToDtoTransformer operationEntityToDtoTransformer;

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private RateService rateService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OperationServiceImpl operationService;

    @Test
    void addOperation_whenCvvIsInvalid_ThenThrowResponseStatusException() {
        final var creditCardId = 1;
        final var cvv = "123";
        final var details = "details";
        final var amount = 100;
        final var operationRequest = new OperationRequest(amount, details, cvv);

        final var creditCardEntity = new CreditCardEntity();
        creditCardEntity.setCvv("321");

        when(creditCardService.getCreditCardEntityById(eq(creditCardId)))
            .thenReturn(creditCardEntity);

        assertThatThrownBy(() ->operationService.addOperation(creditCardId, operationRequest))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessage("400 BAD_REQUEST \"The CVV is not valid\"");

        verify(creditCardService).getCreditCardEntityById(eq(creditCardId));
    }

    @Test
    void addOperation_whenOk_thenCreateOperationAndSendEmailToUser() {
        final var creditCardId = 1;
        final var creditCardBrand = CreditCardBrand.AMEX;
        final var cardNumber = "123456789123456";
        final var maskedCardNumber = "1234XXXXXXXXXXXX";
        final var userEmail = "nicolas.salvai@example.com";
        final var cvv = "123";
        final var details = "details";
        final var amount = 100d;
        final var operationRequest = new OperationRequest(amount, details, cvv);

        final var userEntity = new UserEntity();
        userEntity.setEmail(userEmail);

        final var creditCardEntity = new CreditCardEntity();
        creditCardEntity.setCvv(cvv);
        creditCardEntity.setBrand(creditCardBrand);
        creditCardEntity.setUserEntity(userEntity);
        creditCardEntity.setNumber(cardNumber);

        final var operationRateDto = new OperationRateDto(5d, 5d, 105d);

        final var expectedOperationEntity = new OperationEntity();
        expectedOperationEntity.setAmount(amount);
        expectedOperationEntity.setDetail(details);
        expectedOperationEntity.setTotal(operationRateDto.finalAmount());
        expectedOperationEntity.setInterestRate(operationRateDto.rate());
        expectedOperationEntity.setCreditCardEntity(creditCardEntity);

        final var operationEntityDto = new OperationDto(1,amount,5,105,details,creditCardId);

        when(creditCardService.getCreditCardEntityById(eq(creditCardId)))
            .thenReturn(creditCardEntity);
        when(rateService.getOperationRate(eq(creditCardBrand), eq(amount)))
            .thenReturn(operationRateDto);
        when(operationRepository.save(argThat(receivedOperationEntity ->
                validateOperationEntitiesEqual(receivedOperationEntity, expectedOperationEntity))))
            .thenReturn(expectedOperationEntity);
        when(operationEntityToDtoTransformer.convert(eq(expectedOperationEntity)))
            .thenReturn(operationEntityDto);
        when(creditCardService.maskCreditCardNumber(eq(cardNumber))).thenReturn(maskedCardNumber);

        final var result = operationService.addOperation(creditCardId, operationRequest);

        assertThat(result).isNotNull().isEqualTo(operationEntityDto);

        verify(creditCardService).getCreditCardEntityById(eq(creditCardId));
        verify(rateService).getOperationRate(eq(creditCardBrand), eq(amount));
        verify(operationRepository).save(argThat(receivedOperationEntity ->
            validateOperationEntitiesEqual(receivedOperationEntity, expectedOperationEntity)));
        verify(emailService).sendEmail(eq(userEmail), eq("New operation detected on your Credit Card"), eq("""
                There was an operation detected on your AMEX credit card:
                PAN: 1234XXXXXXXXXXXX
                Amount: 100.0
                Interest rate: 5.0
                Total amount: 105.0
                Detail: details"""));
    }

    private boolean validateOperationEntitiesEqual(OperationEntity op1, OperationEntity op2){
        return op1.getAmount() == op2.getAmount()
            && op1.getDetail().equals(op2.getDetail())
            && op1.getTotal() == op2.getTotal()
            && op1.getInterestRate() == op2.getInterestRate()
            && op1.getCreditCardEntity() == op2.getCreditCardEntity();
    }
}