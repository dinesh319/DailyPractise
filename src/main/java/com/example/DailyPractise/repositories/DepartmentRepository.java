package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity , Long> {
}
