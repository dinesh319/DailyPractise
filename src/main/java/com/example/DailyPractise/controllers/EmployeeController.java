package com.example.DailyPractise.controllers;


import com.example.DailyPractise.DTO.EmployeeDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
        return  new EmployeeDto( employeeId , "dinesh hanumanthu" , "dineshhanumanthu@gmail.com" ,24,"Male" ,LocalDate.of(2023,06,16),true);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees(@RequestParam(required = false) boolean isSorted){

        EmployeeDto[] employees = {
                new EmployeeDto(1L, "John Doe", "john.doe@example.com", 30, "Male", LocalDate.of(2020, 5, 15), true),
                new EmployeeDto(2L, "Jane Smith", "jane.smith@example.com", 28, "Female", LocalDate.of(2019, 8, 21), true),
                new EmployeeDto(3L, "Mike Johnson", "mike.johnson@example.com", 35, "Male", LocalDate.of(2018, 3, 10), false),
                new EmployeeDto(4L, "Emily Davis", "emily.davis@example.com", 25, "Female", LocalDate.of(2022, 1, 5), true)
        };

        List<EmployeeDto> emp;

        if(isSorted){
            emp = Stream.of(employees).sorted((e1,e2) -> e1.getName().compareToIgnoreCase(e2.getName())).toList();
            }
        else {
             emp = Stream.of(employees).toList();

        }
        return emp;
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employee){
        return employee;
    }

    @DeleteMapping(path = "/{employeeId}")
    public String deleteEmployee(@PathVariable(name = "employeeId") Long id){
        return "employee with id "+id +" is deleted";
    }

}
