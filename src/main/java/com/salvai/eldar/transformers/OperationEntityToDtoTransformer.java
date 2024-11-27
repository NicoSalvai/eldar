package com.salvai.eldar.transformers;

import com.salvai.eldar.models.api.OperationDto;
import com.salvai.eldar.models.jpa.OperationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationEntityToDtoTransformer implements Converter<OperationEntity, OperationDto> {

    @Override
    public OperationDto convert(OperationEntity source) {
        return new OperationDto(
            source.getId(),
            source.getAmount(),
            source.getInterestRate(),
            source.getTotal(),
            source.getDetail(),
            source.getCreditCardEntity().getId());
    }
}
