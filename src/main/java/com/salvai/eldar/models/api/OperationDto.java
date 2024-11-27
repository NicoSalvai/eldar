package com.salvai.eldar.models.api;

public record OperationDto(
    Integer id,
    double amount,
    double interestRate,
    double total,
    String detail,
    Integer cardId) {
}
