package com.example.DailyPractise.customvalidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GenderValidator implements ConstraintValidator<GenderValidation , String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null ) return false;

        List<String> genderTypes = List.of("male","female" , "others");
        return genderTypes.contains(s);
    }
}
