package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.DepartmentDto;
import com.example.DailyPractise.entities.DepartmentEntity;
import com.example.DailyPractise.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto departmentDto){
        DepartmentDto savedDepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment , HttpStatus.CREATED);
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "departmentId") Long departmentId){
        DepartmentDto departmentDto = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(departmentDto , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> departmentDtos = departmentService.getAlldepartments();
        return new ResponseEntity<>(departmentDtos , HttpStatus.OK);
    }


    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public ResponseEntity<DepartmentDto> assignManager(@PathVariable(name = "departmentId") Long departmentId,
                                                       @PathVariable(name = "employeeId") Long employeeId){
        DepartmentDto departmentDto = departmentService.assignManager(departmentId , employeeId);
        return new ResponseEntity<>(departmentDto , HttpStatus.OK);
    }

    @PutMapping(path="/{departmentId}/employee/{employeeId}")
    public ResponseEntity<ApiResponse<?>>  assignDepartmentToEmployee(@PathVariable(name = "departmentId") Long departmentId,
                                                                   @PathVariable(name = "employeeId") Long employeeId){
         departmentService.assignDepartmentToEmployee(departmentId , employeeId);
        return new ResponseEntity<>(new ApiResponse<>("department assigned") , HttpStatus.OK);
    }
}
