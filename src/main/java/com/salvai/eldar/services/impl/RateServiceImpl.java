package com.salvai.eldar.services.impl;

import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.jpa.UserEntity;
import com.salvai.eldar.repositories.UserRepository;
import com.salvai.eldar.services.RateService;
import com.salvai.eldar.services.UserService;
import com.salvai.eldar.services.rates.RatesCalculator;
import com.salvai.eldar.transformers.UserEntityToDtoTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    @Override
    public OperationRateDto getOperationRate(CreditCardBrand creditCardBrand, double amount) {
        final var rate = RatesCalculator.getRatesCalculator(creditCardBrand).calculateRate(LocalDate.now());
        final var interest = amount * (rate/100);
        final var total = amount + interest;
        return new OperationRateDto(rate, interest, total);
    }
}
