package com.example.DailyPractise.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"email"})
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    private Double salary;

    private LocalDate  doj;

    @CreationTimestamp
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
