package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto createEmployee(EmployeeDto employee) {
        EmployeeEntity employeeEntity = modelMapper.map( employee , EmployeeEntity.class );
        return modelMapper.map(employeeRepository.save(employeeEntity) , EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {

        List<EmployeeEntity> employees = employeeRepository.findAll();
        return  employees.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class))
                .toList();
    }

    public Optional<EmployeeDto> getEmployeeById(Long employeeId) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(employeeId);
        return  employee.map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class));
    }



  private boolean isEmployeePresent(Long employeeId){
        return employeeRepository.existsById(employeeId);
  }


    public boolean deleteEmployee(Long employeeId) {
        boolean isExists = employeeRepository.existsById(employeeId);
        if(! isExists){
            return  false;
        }

        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        // need to add exception if employee is not present , below when doesnot work when employee is not present

        EmployeeEntity employeeEntity = modelMapper.map(employeeDto ,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    public EmployeeDto partiallyUpdateEmployee(Map<String, Object> updates, Long employeeId) {
        if(!isEmployeePresent(employeeId))
            return null;

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((key , value) -> {
            Field requiredToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class , key);
            requiredToUpdate.setAccessible(true);
            ReflectionUtils.setField(requiredToUpdate,employeeEntity,value);
        });

        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }
}
