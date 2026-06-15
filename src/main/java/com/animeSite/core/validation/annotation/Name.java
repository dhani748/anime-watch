package com.animeSite.core.validation.annotation;

import com.animeSite.core.validation.validator.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface Name {
    String message() default "${validatedValue} must contain only alphabetic characters and spaces.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
