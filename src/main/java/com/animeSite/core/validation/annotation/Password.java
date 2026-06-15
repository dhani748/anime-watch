package com.animeSite.core.validation.annotation;

import com.animeSite.core.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "${validatedValue} must be at least 8 characters with uppercase, lowercase, and digit.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
