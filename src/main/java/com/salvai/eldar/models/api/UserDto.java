package com.salvai.eldar.models.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class UserDto {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Integer dni;
    private final LocalDate birthDate;
    private final String email;
}
