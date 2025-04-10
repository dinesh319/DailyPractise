package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeData){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeData);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long id){
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<ApiResponse<String>> deleteEmployeeById(@PathVariable(name = "employeeId") Long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(new ApiResponse<>("employee with id "+id+" deleted successfully"), HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody Map<String,Object> employeeData,
                                                      @PathVariable(name = "employeeId") Long id){
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeData , id);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }
}
