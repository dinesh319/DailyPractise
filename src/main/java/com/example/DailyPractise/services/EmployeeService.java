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
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    private boolean isEmployeeExists(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }

    public EmployeeDto createEmployee(@Valid EmployeeDto employeeDto) {
        EmployeeEntity employee = modelMapper.map(employeeDto , EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employee) , EmployeeDto.class);
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
            Optional<EmployeeEntity> employee = employeeRepository.findById(id);
            return employee.map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class));
    }

    public List<EmployeeDto> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).toList();
    }



    public boolean deleteEmployee(Long employeeId) {

        if(! isEmployeeExists(employeeId)) throw new ResourceNotFoundException("employee with id: "+employeeId+ " doesnot exists");

        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDto partiallyEmployeeUpdate(Map<String, Object> data, Long employeeId) {

        if(! isEmployeeExists(employeeId)) throw new ResourceNotFoundException("employee with id: "+employeeId+" does not exists");

        EmployeeEntity employee = employeeRepository.findById(employeeId).get();

        data.forEach((key,value) ->{
            Field requiredToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class , key);
            requiredToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(requiredToBeUpdated,employee,value);
        });

        EmployeeEntity changedEmployee = employeeRepository.save(employee);
        return modelMapper.map(changedEmployee,EmployeeDto.class);
    }
}
