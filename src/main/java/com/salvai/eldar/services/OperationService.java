package com.salvai.eldar.services;

import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.OperationRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;

public interface OperationService {
    OperationDto addOperation(Integer creditCardId, OperationRequest operationRequest);

    OperationDto getOperation(Integer operationId);
}
