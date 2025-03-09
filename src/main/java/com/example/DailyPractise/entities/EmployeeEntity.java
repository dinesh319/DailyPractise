package com.example.DailyPractise.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees",
    uniqueConstraints = {
            @UniqueConstraint( columnNames = {"email"})
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(nullable = false ,length = 255)
    private String name;

    @Column(nullable = false , length = 255)
    private String email;

    @Column(nullable = false , length = 10)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    private Double salary;

    private LocalDate doj;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
