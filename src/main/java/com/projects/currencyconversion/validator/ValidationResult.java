package com.projects.currencyconversion.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationResult {

    private final List<ValidationError> errors;

    public ValidationResult() {
        this.errors = new ArrayList<>();
    }

    public void add(ValidationError error) {
        this.errors.add(error);
    }

    public void add(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public String getMessage() {
        return this.errors.stream()
                .map(error -> error.code() + ": " + error.message())
                .collect(Collectors.joining(";"));
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
               "errors=" + errors +
               '}';
    }
}
