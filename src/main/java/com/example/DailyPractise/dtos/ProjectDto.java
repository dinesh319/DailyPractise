package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private String name;

    private Set<EmployeeEntity> projectMembers;

}
