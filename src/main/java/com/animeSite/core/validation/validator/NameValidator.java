package com.animeSite.core.validation.validator;

import com.animeSite.core.validation.annotation.Name;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final String NAME_PATTERN = "^[A-Za-z ]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        return value.matches(NAME_PATTERN);
    }
}
