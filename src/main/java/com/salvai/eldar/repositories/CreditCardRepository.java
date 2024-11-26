package com.salvai.eldar.repositories;

import com.salvai.eldar.models.enums.CreditCardBrand;
import com.salvai.eldar.models.jpa.CreditCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Integer> {

    Page<CreditCardEntity> findAll(Pageable pageable);

    Optional<CreditCardEntity> findByBrandAndNumberAndExpirationDateAfter(CreditCardBrand brand, String number, LocalDate expirationDate);
}
