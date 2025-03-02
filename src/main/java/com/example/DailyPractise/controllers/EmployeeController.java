package com.example.DailyPractise.controllers;


import com.example.DailyPractise.dto.EmployeeDto;
import com.example.DailyPractise.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @DeleteMapping(path = "/{employeeId}")
    public String deleteEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
         employeeService.deleteEmployeeById(employeeId);
         return "employee with id "+employeeId+" deleted successfully";
    }
}
