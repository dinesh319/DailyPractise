package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.DepartmentDto;
import com.example.DailyPractise.entities.DepartmentEntity;
import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.DepartmentRepository;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto ,DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentEntity) , DepartmentDto.class);
    }


    public DepartmentDto getDepartmentById(Long departmentId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
       return departmentEntity.map(departmentEntity1 -> modelMapper.map(departmentEntity1,DepartmentDto.class)).orElseThrow(()-> new ResourceNotFoundException("departmnet with id : "+departmentId +" is not present"));

    }

    public DepartmentDto assignManager(Long employeeId, Long departmentId) {

        /*
        // one way of assigning the manager
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new ResourceNotFoundException("department with id : "+departmentId +" does not exists"));

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("employee with id :"+employeeId+" does not exists"));

        department.setManager(employee);
        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment ,DepartmentDto.class);

        */
        // other way of assigning manager

       DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
               .flatMap(department -> employeeRepository.findById(employeeId).map(employee->{
                    department.setManager(employee);
                    departmentRepository.save(department);
                    return department;
               })
               ).orElseThrow(()-> new ResourceNotFoundException("department with id :"+departmentId+" does not exists"));

       return modelMapper.map(departmentEntity , DepartmentDto.class);

    }

    public DepartmentDto getAssignedDepartmentForEmployee(Long employeeId) {

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId+" does not exists"));
        DepartmentEntity managedDepartment = employeeEntity.getMangedDepartment();
        return modelMapper.map(managedDepartment,DepartmentDto.class);
    }
}
