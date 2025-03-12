package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.DepartmentDto;
import com.example.DailyPractise.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto savedDepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment , HttpStatus.CREATED);
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "departmentId") Long departmentId){
        DepartmentDto departmentDto = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(departmentDto , HttpStatus.OK);
    }

    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public ResponseEntity<DepartmentDto> assignManager(@PathVariable(name = "employeeId") Long employeeId,
                                                       @PathVariable(name = "departmentId") Long departmentId){

        DepartmentDto department = departmentService.assignManager(employeeId , departmentId);
        return new ResponseEntity<>(department , HttpStatus.OK);

    }

    @PutMapping(path = "/assignedDepartment/{employeeId}")
    public ResponseEntity<DepartmentDto> assignedDepartmentForEmployee(@PathVariable(name = "employeeId") Long employeeId){
        DepartmentDto department = departmentService.getAssignedDepartmentForEmployee(employeeId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
