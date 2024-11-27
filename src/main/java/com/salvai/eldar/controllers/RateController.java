package com.salvai.eldar.controllers;

import com.salvai.eldar.models.api.OperationRateDto;
import com.salvai.eldar.models.enums.CreditCardBrand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@Tag(name = "Rates", description = "Operation rates management resources")
public interface RateController {

    @Operation(summary = "Get operation rate")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operation rate available"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/operation-rates")
    ResponseEntity<OperationRateDto> getOperationRate(@RequestParam CreditCardBrand creditCardBrand,
        @Max(value = 10_000, message = "An operation above 10.000 is not allowed") @RequestParam double amount);
}
