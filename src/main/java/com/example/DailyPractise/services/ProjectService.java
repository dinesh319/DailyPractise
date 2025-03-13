package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.ProjectDto;
import com.example.DailyPractise.entities.ProjectEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import com.example.DailyPractise.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = modelMapper.map(projectDto , ProjectEntity.class);
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return modelMapper.map(savedProject,ProjectDto.class);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectEntity -> modelMapper.map(projectEntity,ProjectDto.class)).toList();
    }

    public ProjectDto getProjectById(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow(()->new ResourceNotFoundException("project with id : "+projectId+" is not found"));

        return modelMapper.map(projectEntity,ProjectDto.class);
    }

    public ProjectDto assignEmployeeToProject(Long projectId, Long employeeId) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        ProjectEntity savedProject = projectEntity.map(projectEntity1 -> {
            return employeeRepository.findById(employeeId).map(employeeEntity -> {
                projectEntity1.getProjectMembers().add(employeeEntity);
                return projectRepository.save(projectEntity1);
            }).orElseThrow(()-> new ResourceNotFoundException("employee with id : "+employeeId+" does not exists"));
        }).orElseThrow(()->new ResourceNotFoundException("project with id : "+projectId+" does not exists"));

        return modelMapper.map(savedProject , ProjectDto.class);
    }

    public void deleteProject(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("project with id : "+projectId+" does not exists"));

        projectRepository.delete(projectEntity);
    }
}
