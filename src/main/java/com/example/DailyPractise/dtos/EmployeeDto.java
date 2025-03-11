package com.example.DailyPractise.dtos;

import com.example.DailyPractise.customvalidations.GenderValidation;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Gender is required.")
    @GenderValidation
    private String gender;

    @Min(value = 18, message = "Age must be at least 18.")
    @Max(value = 60, message = "Age must not exceed 60.")
    private Integer age;

    @DecimalMin(value = "100.50", message = "Salary must be at least 100.50.")
    @DecimalMax(value = "10000000.50", message = "Salary must not exceed 10000000.50.") // Fixed the issue (used @DecimalMax)
    private Double salary;

    @PastOrPresent(message = "Date of joining must be in the past or present.")
    private LocalDate doj;
}
