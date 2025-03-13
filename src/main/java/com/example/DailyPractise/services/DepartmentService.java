package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.DepartmentDto;
import com.example.DailyPractise.entities.DepartmentEntity;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.DepartmentRepository;
import com.example.DailyPractise.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final ModelMapper modelMapper;

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public DepartmentService(ModelMapper modelMapper, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentDto createDepartment(@Valid DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto , DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentEntity),DepartmentDto.class);
    }

    public DepartmentDto getDepartmentById(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("department with id :"+departmentId+" does not exists"));
        return modelMapper.map(departmentEntity,DepartmentDto.class);
    }

    public List<DepartmentDto> getAlldepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream().map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDto.class)).toList();
    }

    public DepartmentDto assignManager(Long departmentId, Long employeeId) {
//        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("department with id : "+departmentId+" is not found"));
//        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId+" is not found"));
//
//        departmentEntity.setManager(employeeEntity);
//        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
//        return modelMapper.map(savedDepartment,DepartmentDto.class);

        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);

        DepartmentEntity savedDepartment = departmentEntity.map(departmentEntity1 -> {
           return employeeRepository.findById(employeeId).map(employeeEntity -> {
               departmentEntity1.setManager(employeeEntity);
               return departmentRepository.save(departmentEntity1);
           }).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId+" doesnot exists"));
        }).orElseThrow(()-> new  ResourceNotFoundException("department with id : "+departmentId+" doesnot exists"));


        return modelMapper.map(savedDepartment , DepartmentDto.class);
    }


    public void assignDepartmentToEmployee(Long departmentId, Long employeeId) {

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        employeeEntity.map(employeeEntity1 -> {
            return departmentRepository.findById(departmentId).map(departmentEntity -> {
                employeeEntity1.setDepartment(departmentEntity);
                return employeeRepository.save(employeeEntity1);
            }).orElseThrow( ()-> new ResourceNotFoundException("employee with id :"+employeeId+" does not exists"));
        }).orElseThrow( ()-> new ResourceNotFoundException("department with id :"+departmentId+" does not exists"));

    }
}
