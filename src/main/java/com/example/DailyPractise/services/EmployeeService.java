package com.example.DailyPractise.services;


import com.example.DailyPractise.dto.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDto createEmployee(EmployeeDto employee) {
        EmployeeEntity employeeEntity = modelMapper.map(employee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity , EmployeeDto.class);
    }

    public EmployeeDto getEmployeeById(Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        return modelMapper.map(employeeEntity,EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> entities = employeeRepository.findAll();
        List<EmployeeDto> employees = entities.stream().
                map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class))
                .toList();
        return employees;

    }

    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
