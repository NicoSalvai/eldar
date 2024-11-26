package com.salvai.eldar.models.jpa;

import com.salvai.eldar.models.enums.CreditCardBrand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCardEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private CreditCardBrand brand;
    private String number;
    private String cvv;
    private LocalDate expirationDate;

    @ManyToOne
    private UserEntity userEntity;
}
