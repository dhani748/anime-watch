package com.animeSite.core.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotBlank
public @interface NoEmpty {
    String message() default "Field must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
