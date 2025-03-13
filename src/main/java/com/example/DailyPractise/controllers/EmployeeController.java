package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid  EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee , HttpStatus.CREATED);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeebyId(employeeId);
        return new ResponseEntity<>(employeeDto , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @PutMapping(path = "/{employeeId}/department/{departmentId}")
    public ResponseEntity<EmployeeDto> assignEmployeeToDepartment(@PathVariable(name="employeeId") Long employeeId,
                                                                  @PathVariable(name = "departmentId") Long departmentId){
        EmployeeDto employee = employeeService.assignEmployeeToDepartment(employeeId , departmentId);
        return new ResponseEntity<>(employee , HttpStatus.OK);
    }
}
