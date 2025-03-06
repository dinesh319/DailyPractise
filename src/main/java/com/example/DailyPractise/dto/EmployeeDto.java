package com.example.DailyPractise.dto;

import com.example.DailyPractise.customvalidations.GenderValidation;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters.")
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Gender cannot be blank.")
    @GenderValidation
    private String gender;

    @NotNull(message = "Age cannot be null.")
    @Min(value = 18, message = "Age must be at least 18 years.")
    @Max(value = 60, message = "Age must be at most 60 years.")
    private Integer age;

    @DecimalMin(value = "100.50", message = "Salary must be at least 100.50.")
    @DecimalMax(value = "10000000.50", message = "Salary must be at most 10,000,000.50.")
    private Double salary;

    @Past(message = "Date of joining must be in the past.")
    private LocalDate doj;

}
