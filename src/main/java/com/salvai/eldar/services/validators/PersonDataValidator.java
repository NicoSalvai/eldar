package com.salvai.eldar.services.validators;

import java.time.LocalDate;

public class PersonDataValidator extends Validator {

    private static final String FIRST_NAME = "Nombre";
    private static final String LAST_NAME = "Apellido";
    private static final String DNI = "DNI";
    private static final String BIRTH_DATE = "Fecha de Nacimiento";
    private static final String EMAIL = "Email";
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DNI_LENGTH = 20;

    private final String firstName;
    private final String lastName;
    private final String dni;
    private final String birthDate;
    private final String email;

    public PersonDataValidator(String firstName, String lastName, String dni, String birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.birthDate = birthDate;
        this.email = email;
    }

    public boolean validate(){
        validateName(firstName, FIRST_NAME);
        validateName(lastName, LAST_NAME);
        validateDni(dni);
        validateBirthDate(birthDate);
        validateEmail(email);
        return errors.isEmpty();
    }

    private void validateName(String name, String propertyName){
        notEmpty(name, propertyName);
        shorterOrEqualTo(name, MAX_NAME_LENGTH, propertyName);
    }

    private void validateDni(String dni){
        notEmpty(dni, DNI);
        shorterOrEqualTo(dni, MAX_DNI_LENGTH, DNI);
    }

    private void validateBirthDate(String birthDate){
        notEmpty(birthDate, BIRTH_DATE);
        shorterOrEqualTo(birthDate, DATE_TIME_FORMAT.length(), BIRTH_DATE);
        isDate(birthDate, BIRTH_DATE);
        dateBefore(birthDate, LocalDate.now(), BIRTH_DATE);
    }

    private void validateEmail(String email) {
        notEmpty(email, EMAIL);
        isEmail(email, EMAIL);
    }
}
