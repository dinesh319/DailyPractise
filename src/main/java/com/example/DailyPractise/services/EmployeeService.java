package com.example.DailyPractise.services;

import com.example.DailyPractise.customexception.ResourceNotFoundException;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    private void isEmployeeExists(Long id){
        if(! employeeRepository.existsById(id))
            throw new ResourceNotFoundException("employee with id :"+id+" doesnot exists");
    }

    public EmployeeDto createEmployee(@Valid EmployeeDto employeeDto) {
        EmployeeEntity employee = modelMapper.map(employeeDto , EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employee) , EmployeeDto.class);
    }

    public EmployeeDto getEmployeeById(Long id) {
        isEmployeeExists(id);

        EmployeeEntity employee = employeeRepository.findById(id).get();
        return modelMapper.map(employee , EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity , EmployeeDto.class))
                .toList();
    }


    public boolean deleteEmployeeById(Long id) {
        isEmployeeExists(id);

        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDto updateEmployee(Map<String, Object> data, Long id) {
        isEmployeeExists(id);

        EmployeeEntity employee = employeeRepository.findById(id).get();

        data.forEach((key ,value)->{
            Field fieldToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class , key);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate , employee , value);
        });

        return modelMapper.map(employeeRepository.save(employee) , EmployeeDto.class);
    }
}
