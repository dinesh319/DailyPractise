package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.ProjectDto;
import com.example.DailyPractise.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto){
        ProjectDto project = projectService.createProject(projectDto);
        return new ResponseEntity<>(project , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<ProjectDto> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects,HttpStatus.OK);
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable(name = "projectId") Long projectId){
        ProjectDto projectDto = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @PutMapping(path = "/{projectId}/assign-project-employee/{employeeId}")
    public ResponseEntity<ProjectDto> assignEmployeeToProject(@PathVariable(name = "projectId") Long projectId,
                                                              @PathVariable(name = "employeeId") Long employeeId){
        ProjectDto projectDto = projectService.assignEmployeeToProject(projectId , employeeId);
        return new ResponseEntity<>(projectDto , HttpStatus.OK);
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable(name = "projectId") Long projectId){
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(new ApiResponse<>("project with id "+projectId+" deleted sucessfully"), HttpStatus.NO_CONTENT);
    }
}
