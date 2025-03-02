package com.example.DailyPractise.dto;

import java.time.LocalDate;

public class EmployeeDto {

    private Long id;

    private String name;

    private String email;

    private String gender;

    private Integer age;

    private LocalDate doj;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String email, String gender, Integer age, LocalDate doj) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.doj = doj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }
}
