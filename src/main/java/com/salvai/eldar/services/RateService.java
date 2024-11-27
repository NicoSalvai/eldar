package com.salvai.eldar.services;

import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.enums.CreditCardBrand;

public interface RateService {
    OperationRateDto getOperationRate(CreditCardBrand creditCardBrand, double amount);
}
