package com.example.DailyPractise.customvalidations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {GenderValidator.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderValidation  {

    String message() default "gender should be either male , female or other";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
