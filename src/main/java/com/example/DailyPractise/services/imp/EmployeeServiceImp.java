package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import com.example.DailyPractise.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;


    @Override
    @CachePut(cacheNames = "employees" , key = "#result.id")
    public EmployeeDto createEmployee(EmployeeDto employeeData) {
        EmployeeEntity employee = modelMapper.map(employeeData,EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
//    @Cacheable(cacheNames = "employees", key = "all")  // list may change frequently so better to remove caching
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Cacheable(cacheNames = "employees", key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee with id "+id+" does not exists"));
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "employees", key = "#id")
    public void deleteEmployeeById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee with id "+id+" does not exists"));
        employeeRepository.deleteById(id);
    }

    @Override
    @CachePut(cacheNames = "employees" , key = "#id")
    public EmployeeDto updateEmployee(Map<String, Object> employeeData, Long id) {

        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee with id "+id+" does not exists"));

        employeeData.forEach((key,value) ->{
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employee,value);
        });

        EmployeeEntity updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee,EmployeeDto.class);

    }
}
