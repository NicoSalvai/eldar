package com.salvai.eldar.controllers;

import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.api.OperationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping()
@Tag(name = "Operations", description = "Operation management resources")
public interface OperationController {

    @Operation(summary = "Add operation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operation created"),
            @ApiResponse(responseCode = "400", description = "Problem with request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/credit-cards/{creditCardId}/operations")
    ResponseEntity<Void> addOperation(
        @Parameter @PathVariable("creditCardId") Integer creditCardId,
        @Valid @RequestBody OperationRequest operationRequest);

    @Operation(summary = "Get operation by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation found"),
            @ApiResponse(responseCode = "400", description = "Problem with request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/operations/{operationId}")
    ResponseEntity<OperationDto> getOperation(@Parameter @PathVariable("operationId") Integer operationId);
}
