package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.EmployeeDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    EmployeeDto createEmployee(@Valid EmployeeDto employeeData);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    EmployeeDto updateEmployee(Map<String, Object> employeeData, Long id);

}
