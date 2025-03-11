package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.EmployeeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Long> {


    List<EmployeeEntity> findByNameOrderByAge(String name);
}
