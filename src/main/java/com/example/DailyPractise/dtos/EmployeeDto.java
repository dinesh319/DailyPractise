package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.DepartmentEntity;
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

    private String name;

    private String email;

    private String gender;

    private Integer age;

    private Double salary;

    private LocalDate doj;

    private DepartmentEntity mangedDepartment;

}
