package com.salvai.eldar.controllers;

import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.api.CreditCardRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/credit-cards")
@Tag(name = "Credit Cards", description = "Credit card management resources")
public interface CreditCardController {

    @Operation(summary = "Create a new credit card")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Credit card created successfully"),
        @ApiResponse(responseCode = "400", description = "Problem with request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<Void> addCreditCard(@Valid @RequestBody CreditCardRequest creditCardRequest);

    @Operation(summary = "Get Credit Cards")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Credit cards found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<Page<CreditCardDto>> getCreditCards(@ParameterObject @PageableDefault Pageable pageable);

    @Operation(summary = "Get credit card by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Credit card found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "404", description = "Credit card not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{creditCardId}")
    ResponseEntity<CreditCardDto> getCreditCardById(@Parameter @PathVariable("creditCardId") Integer creditCardId);

    @Operation(summary = "Delete credit card by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Credit card found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "404", description = "CreditCard not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{creditCardId}")
    ResponseEntity<Void> deleteCreditCardById(@Parameter @PathVariable("creditCardId") Integer creditCardId);
}
