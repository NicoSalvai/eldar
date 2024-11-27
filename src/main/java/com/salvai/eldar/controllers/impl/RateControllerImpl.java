package com.salvai.eldar.controllers.impl;

import com.salvai.eldar.controllers.RateController;
import com.salvai.eldar.controllers.UserController;
import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.services.RateService;
import com.salvai.eldar.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class RateControllerImpl implements RateController {

    @NonNull
    private final RateService rateService;

    @Override
    public ResponseEntity<OperationRateDto> getOperationRate(CreditCardBrand creditCardBrand, double amount) {
        return ResponseEntity.ok(rateService.getOperationRate(creditCardBrand, amount));
    }
}
