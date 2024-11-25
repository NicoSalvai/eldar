package com.salvai.eldar.services.validators;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class Validator {

    protected static final String DATE_TIME_FORMAT = "dd-MM-yyyy";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    protected final List<String> errors;

    public Validator() {
        this.errors = new ArrayList<>();
    }

    public abstract boolean validate();

    protected void notEmpty(String string, String propertyName) {
        if(string == null) {
            errors.add("%s no puede ser nulo".formatted(propertyName));
            return;
        }
        if(string.isEmpty()) {
            errors.add("%s no puede estar vacio".formatted(propertyName));
        }
    }

    protected void shorterOrEqualTo(String string, int maxLength, String propertyName) {
        if(string.length() > maxLength){
            errors.add("%s deberia de ser de %d caracteres o menos".formatted(propertyName, maxLength));
        }
    }

    protected void isDate(String date, String propertyName){
        try {
            LocalDate.parse(date, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex){
            errors.add("%s deberia ser una fecha valida segun el formato (%s)".formatted(propertyName, DATE_TIME_FORMAT));
        }
    }

    protected void isEmail(String email, String propertyName){
        if(!EmailValidator.getInstance().isValid(email)){
            errors.add("%s no es un email valido".formatted(propertyName));
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
