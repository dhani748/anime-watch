package com.animeSite.core.validation.annotation;

import com.animeSite.core.validation.validator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface Phone {
    String message() default "${validatedValue} is not a valid phone number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
