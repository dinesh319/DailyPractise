package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long id){
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDto , HttpStatus.OK);
    }


    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long employeeId) {
        boolean isDeleted = employeeService.deleteEmployeeById(employeeId);

        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>("Employee with ID " + employeeId + " deleted successfully"));
        } else {
            throw new ResourceNotFoundException("Employee with ID " + employeeId + " not found.");
        }
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody Map<String , Object> data ,
                                                      @PathVariable(name = "employeeId") Long id){

        EmployeeDto employeeDto = employeeService.updateEmployee(data , id);
        return new ResponseEntity<>(employeeDto , HttpStatus.OK);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByName(@RequestParam String name) {
        List<EmployeeDto> employees =  employeeService.getEmployeesByName(name);
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByAge(@RequestParam Integer age){
        List<EmployeeDto> employees = employeeService.geEmployeeByTheirAge(age);
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

}
