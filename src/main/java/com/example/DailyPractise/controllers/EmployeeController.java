package com.example.DailyPractise.controllers;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return new ResponseEntity<EmployeeDto>(savedEmployee , HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDto> employee = employeeService.getEmployeeById(id);
        return
                employee.map(employeeDto -> new ResponseEntity<>(employeeDto , HttpStatus.OK))
                        .orElseThrow(() -> new ResourceNotFoundException("employee with id :" + id + " doesnot exists"));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees , HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId){
        boolean isDeleted = employeeService.deleteEmployee(employeeId);
        if(isDeleted){
            return new ResponseEntity<>("employee with id : "+employeeId +" deleted successfully" , HttpStatus.OK);
        }else{
            return new ResponseEntity<>("something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> partiallyEmployeeUpdate(@RequestBody Map<String , Object> data , @PathVariable Long employeeId){
        EmployeeDto modifiedEmployee = employeeService.partiallyEmployeeUpdate(data , employeeId);
        return new ResponseEntity<>(modifiedEmployee , HttpStatus.OK);
    }


    // for handling something locally in each controller
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException){
//        return new ResponseEntity<>(noSuchElementException.getMessage() , HttpStatus.BAD_REQUEST);
//    }
}
