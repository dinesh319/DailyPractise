package com.example.DailyPractise.dtos;

import com.example.DailyPractise.customvalidations.GenderValidation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Gender cannot be blank")
    @GenderValidation
    private String gender;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age cannot exceed 60")
    private Integer age;

    @DecimalMin(value = "100.50", message = "Salary must be at least 100.50")
    @DecimalMax(value = "1000000.50", message = "Salary cannot exceed 1,000,000.50")
    private Double salary;

    @PastOrPresent(message = "Date of joining must be in the past or present")
    private LocalDate doj;
}
