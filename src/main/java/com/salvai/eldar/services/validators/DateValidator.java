package com.salvai.eldar.services.validators;

public class DateValidator extends Validator {

    private final static String DATE = "Fecha";
    private final String date;

    public DateValidator(String date) {
        this.date = date;
    }

    public boolean isValid(){
        validateDate(date);
        return !errors.isEmpty();
    }

    private void validateDate(String expirationDate){
        notEmpty(expirationDate, DATE);
        shorterOrEqualTo(expirationDate, DATE_TIME_FORMAT.length(), DATE);
        isDate(expirationDate, DATE);
    }
}
