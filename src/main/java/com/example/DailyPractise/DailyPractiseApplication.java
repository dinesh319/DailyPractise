package com.example.DailyPractise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyPractiseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DailyPractiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("welcome dinesh hanumanthu");
		System.out.println("1. filter even numbers , odd numbers from the list");
		System.out.println("2. find maximum , minimum  from the list");
		System.out.println("3. find sum of elements from the list");
		System.out.println("4. Names to uppercase");
		System.out.println("5. sort integers asc , desc");
		System.out.println("6. count numbers that are greater than 5 and less than 5");
		System.out.println("7. get distinct elements");
		System.out.println("8. sum using reduce");
		System.out.println("9. Return any element from a list of integers");
		System.out.println("10. Extract first names from a list of full names. ");

		System.out.println("11. Check if all numbers in a list are positive. ");
		System.out.println("12. Check if there are no negative numbers in a list");
		System.out.println("13. Find the first element in a list of integers. ");
		System.out.println("14. Flatten a nested list structure. ");
		System.out.println("15. Group users by age. ");


	}
}
