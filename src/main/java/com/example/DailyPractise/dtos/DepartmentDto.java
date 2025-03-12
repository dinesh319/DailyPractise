package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private String name;

    private EmployeeEntity manager;
}
