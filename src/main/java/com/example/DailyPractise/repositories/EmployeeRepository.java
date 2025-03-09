package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Long> {
    List<EmployeeEntity> getEmployeesByName(String name);

    @Query("select e from EmployeeEntity e where age= :age")
    List<EmployeeEntity>getEmployeesByTheirAge(Integer age);

    List<EmployeeEntity> getEmployeesBySalaryAndName(double salary, String name);

    EmployeeEntity findByNameOrEmail(String name, String email);

    List<EmployeeEntity> findEmployeesByNameContaining(String word);

    List<EmployeeEntity> findByNameEndingWith(String word);
}
