package com.example.DailyPractise.customvalidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GenderValidator implements ConstraintValidator<GenderValidation,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String>genderTypes = List.of("male","female","other");
        return genderTypes.contains(s);
    }
}
