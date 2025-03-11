package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public List<EmployeeDto> getAllEmployees(String sortingParameter) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(Sort.by(sortingParameter));
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }


    public List<EmployeeDto> getEmployees(String sortingParameter) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(Sort.by(Sort.Direction.DESC,sortingParameter));
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = modelMapper.map(employeeDto , EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee , EmployeeDto.class);
    }

    public List<EmployeeDto> findByNameOrderByAge(String name) {

        List<EmployeeEntity> employeeEntities = employeeRepository.findByNameOrderByAge(name);

        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }

    public List<EmployeeDto> getAllEmployeesPagination(String pageNumber) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber),2,Sort.by(Sort.Direction.DESC,"id"));
        Page<EmployeeEntity> employees = employeeRepository.findAll(pageable);

        return employees.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }
}
