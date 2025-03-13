package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.EmployeeDto;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.DepartmentRepository;
import com.example.DailyPractise.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeDto createEmployee(@Valid EmployeeDto employeeDto) {
        EmployeeEntity employee = modelMapper.map(employeeDto ,EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employee),EmployeeDto.class);
    }

    public EmployeeDto getEmployeebyId(Long employeeId) {
        EmployeeEntity employee = employeeRepository
                .findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId +" does not exists"));
        return modelMapper.map(employee,EmployeeDto.class);

    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity , EmployeeDto.class)).toList();
    }

    public EmployeeDto assignEmployeeToDepartment(Long employeeId, Long departmentId) {
//        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId+" doesnot exists"));
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        EmployeeEntity savedEmployee = employeeEntity.map(employeeEntity1 -> {
            return departmentRepository.findById(departmentId)
                    .map(departmentEntity -> {
                employeeEntity1.setDepartment(departmentEntity);
                return employeeRepository.save(employeeEntity1);
            }).orElseThrow(()-> new ResourceNotFoundException("employee with id :"+departmentId+" does not exists"));

        }).orElseThrow(()-> new ResourceNotFoundException("employee with id :"+employeeId+" does not exists"));

        return modelMapper.map(savedEmployee , EmployeeDto.class);
    }
}
