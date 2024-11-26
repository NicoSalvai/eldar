package com.salvai.eldar.transformers;

import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.jpa.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoTransformer implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity source) {
        return new UserDto(source.getId(), source.getFirstName(), source.getLastName(),
            source.getDni(), source.getBirthDate(), source.getEmail());
    }
}
