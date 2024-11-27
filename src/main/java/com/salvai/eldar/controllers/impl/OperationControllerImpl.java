package com.salvai.eldar.controllers.impl;

import com.salvai.eldar.controllers.OperationController;
import com.salvai.eldar.controllers.RateController;
import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.OperationRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.services.OperationService;
import com.salvai.eldar.services.RateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class OperationControllerImpl implements OperationController {

    @NonNull
    private final OperationService operationService;

    @Override
    public ResponseEntity<Void> addOperation(Integer creditCardId, OperationRequest operationRequest) {
        final var operationDto = operationService.addOperation(creditCardId, operationRequest);

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(operationDto.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<OperationDto> getOperation(Integer operationId) {
        return ResponseEntity.ok(operationService.getOperation(operationId));
    }
}
