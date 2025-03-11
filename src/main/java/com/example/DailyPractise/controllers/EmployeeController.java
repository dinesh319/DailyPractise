package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.services.EmployeeService;
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
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee , HttpStatus.OK);
    }


    /*  different sorting and fetching techniques
    @GetMapping
    public ResponseEntity<List<EmployeeDto>>findByNameOrderByAge(@RequestParam(name = "search", defaultValue = "") String name){
        List<EmployeeDto> employees = employeeService.findByNameOrderByAge(name);
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(name = "sortBy" , defaultValue = "id") String sortingParameter){
        List<EmployeeDto> employees = employeeService.getEmployees(sortingParameter);
                return new ResponseEntity<>(employees , HttpStatus.OK);

    }

        @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam(name = "sortBy" , defaultValue = "id") String sortingParameter){
        List<EmployeeDto> employees = employeeService.getAllEmployees(sortingParameter);
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }
     */


    /*
            pagination

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesPagination(@RequestParam(name = "pageNumber", defaultValue = "0") String pageNumber){
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployeesPagination(pageNumber);
        return new ResponseEntity<>(employeeDtos , HttpStatus.OK);
    }
     */


}
