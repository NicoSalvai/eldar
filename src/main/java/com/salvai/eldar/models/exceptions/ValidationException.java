package com.salvai.eldar.models.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

    private final List<String> errors;

    public ValidationException(String message) {
        this(message, new ArrayList<>());
    }

    public ValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
