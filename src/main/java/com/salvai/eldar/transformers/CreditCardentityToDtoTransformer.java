package com.salvai.eldar.transformers;

import com.salvai.eldar.models.api.CreditCardDto;
import com.salvai.eldar.models.jpa.CreditCardEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreditCardentityToDtoTransformer implements Converter<CreditCardEntity, CreditCardDto> {

    @Override
    public CreditCardDto convert(CreditCardEntity source) {
        return new CreditCardDto(
            source.getId(),
            source.getBrand(),
            source.getNumber(),
            source.getExpirationDate(),
            source.getUserEntity().getId(),
            source.getUserEntity().getFullName(),
            source.getExpirationDate().isAfter(LocalDate.now()));
    }
}
