package com.example.DailyPractise.controllers;

import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
            return employeeRepository.findById(employeeId).orElse(null);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employee){
        return employeeRepository.save(employee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public String deleteEmployee(@PathVariable(name = "employeeId") Long employeeId){
        employeeRepository.deleteById(employeeId);
        return "employee with id "+ employeeId + " is deleted successfully";
    }



}
