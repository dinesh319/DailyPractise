package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.EmployeeEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    private EmployeeEntity manager;

    private Set<EmployeeEntity> departmentEmployees;
}
