package com.example.DailyPractise;

import com.example.DailyPractise.entities.EmployeeEntity;
import com.example.DailyPractise.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DailyPractiseApplicationTests {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void  getEmployeesByTheirSalaryAndName(){
		List<EmployeeEntity> employeeEntities = employeeRepository.getEmployeesBySalaryAndName(26400.5,"dinesh hanumanthu");
		for(EmployeeEntity employee : employeeEntities){
			System.out.println(employee.getEmail() +" "+ employee.getSalary());
		}
	}

	@Test
	void getEmployeeByNameOrEmail(){
		EmployeeEntity employee = employeeRepository.findByNameOrEmail("dinesh" , "dineshhanumanthu666@gmail.com");
		System.out.println(employee.getEmail() + " "+ employee.getName());
	}

	@Test
	void getEmployeeswithNameContaingWord(){
		List<EmployeeEntity> employeeEntities = employeeRepository.findEmployeesByNameContaining("di");
		for(EmployeeEntity employee : employeeEntities){
			System.out.println(employee.getName() +" "+ employee.getEmail());
		}
	}


	@Test
	void getEmployeeswithNameEndsWithWord(){
		List<EmployeeEntity> employeeEntities = employeeRepository.findByNameEndingWith("hanumanthu");
		for(EmployeeEntity employee : employeeEntities){
			System.out.println(employee.getName() +" "+ employee.getEmail());
		}
	}


}
