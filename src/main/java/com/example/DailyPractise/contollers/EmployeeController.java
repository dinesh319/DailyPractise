package com.example.DailyPractise.contollers;

import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.services.EmployeeService;
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
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee){

        EmployeeDto createdEmployee =  employeeService.createEmployee(employee);
        return new ResponseEntity<EmployeeDto>( createdEmployee, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<List<EmployeeDto>>(employees,HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long employeeId){
        Optional<EmployeeDto> employee = employeeService.getEmployeeById(employeeId);
        return  employee.map(employeeDto -> new ResponseEntity<EmployeeDto>(employeeDto , HttpStatus.OK)).orElse(new ResponseEntity<EmployeeDto>((EmployeeDto) null, HttpStatus.NOT_FOUND));
    }

  @PutMapping(path = "/{employeeId}")  // employee id if not present it breaks add exception later
  public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto , @PathVariable(name = "employeeId") Long employeeId){
        EmployeeDto employeeDto1 = employeeService.updateEmployee(employeeId , employeeDto);
        return new ResponseEntity<EmployeeDto>(employeeDto1 , HttpStatus.OK);
  }

   @PatchMapping(path = "/{employeeId}")
   public ResponseEntity<EmployeeDto> partiallyUpdateEmployee(@RequestBody Map<String , Object> updates , @PathVariable(name = "employeeId") Long employeeId){
        EmployeeDto updatedEmployee = employeeService.partiallyUpdateEmployee(updates , employeeId);
        return new ResponseEntity<EmployeeDto>(updatedEmployee,HttpStatus.OK);
   }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId ){
        boolean bool = employeeService.deleteEmployee(employeeId);
        if(! bool){
            return  new ResponseEntity<String>("employee could not be found "+employeeId , HttpStatus.NOT_FOUND);
        }else{
           return new ResponseEntity<String>("employee with id "+employeeId + " is deleted",HttpStatus.OK);
        }
    }

}
