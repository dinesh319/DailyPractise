package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
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

@Service
public class EmployeeService {

    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    private void isEmployeeExists(Long id) {
        if(! employeeRepository.existsById(id)){
            throw new ResourceNotFoundException("employee with id :"+id+" doesnot exists");
        }
    }


    public EmployeeDto createEmployee(@Valid EmployeeDto employeeDto) {
        EmployeeEntity employee = modelMapper.map(employeeDto , EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employee) , EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class))
                .toList();
    }

    public EmployeeDto getEmployeeById(Long id) {
        isEmployeeExists(id);

        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        return modelMapper.map(employeeEntity,EmployeeDto.class);
    }


    public boolean deleteEmployeeById(Long id) {
        isEmployeeExists(id);

        employeeRepository.deleteById(id);
        return true;

    }

    public EmployeeDto updateEmployee(Map<String, Object> data, Long id) {

        isEmployeeExists(id);

        EmployeeEntity employee = employeeRepository.findById(id).get();

        data.forEach((key , value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employee,value);
        });

        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    public List<EmployeeDto> getEmployeesByName(String name) {

        List<EmployeeEntity> employeeEntities = employeeRepository.getEmployeesByName(name);

        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }

    public List<EmployeeDto> geEmployeeByTheirAge(Integer age) {
        List<EmployeeEntity> employeeEntities = employeeRepository.getEmployeesByTheirAge(age);
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();

    }
}
