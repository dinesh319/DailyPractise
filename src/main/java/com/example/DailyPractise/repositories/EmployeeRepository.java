package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Long> {
}
