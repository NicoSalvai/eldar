package com.salvai.eldar.models.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OperationEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private CreditCardEntity creditCardEntity;
    private double amount;
    private double interestRate;
    private double total;
    private String detail;
}
