package com.salvai.eldar.services;

import com.salvai.eldar.models.Person;
import com.salvai.eldar.models.exceptions.ValidationException;
import com.salvai.eldar.services.validators.PersonDataValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private final List<Person> persons = new ArrayList<>();

    public void registerPerson(String firstName, String lastName, String dni, String birthDate, String email) throws ValidationException {

        final var personDataValidator = new PersonDataValidator(firstName, lastName, dni, birthDate, email);
        if(!personDataValidator.validate()){
            throw new ValidationException("Hubo un problema al registrar la persona, revise los siguientes campos:",
                personDataValidator.getErrors());
        }

        if(persons.stream().anyMatch(person -> person.dni().equals(dni))){
            throw new ValidationException("Ya hay una persona registrada con el dni %s ".formatted(dni));
        }

        final var birthDateAsDate = LocalDate.parse(birthDate, PersonDataValidator.DATE_TIME_FORMATTER);

        persons.add(new Person(firstName, lastName, dni, birthDateAsDate, email));
    }

    public Person getPerson(String dni) throws ValidationException {
        return persons.stream()
            .filter(person -> person.dni().equals(dni)).findFirst()
            .orElseThrow(() -> new ValidationException("No hay una persona registrada con ese DNI: \"%s\"".formatted(dni)));
    }
}
