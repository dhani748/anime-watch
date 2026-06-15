package com.animeSite.core.validation.validator;

import com.animeSite.core.validation.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private static final String PHONE_PATTERN = "^\\+?[1-9]\\d{1,14}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        return value.matches(PHONE_PATTERN);
    }
}
