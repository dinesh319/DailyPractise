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
		System.out.println("Your Spring Security configuration has two conflicting user details services:\n" +
				"\n" +
				"In-Memory Authentication (myInMemoryUserDetailService) in WebSecConfig, which defines hardcoded users (dinesh and admin).\n" +
				"Database Authentication (UserService implementing UserDetailsService), which fetches users from the UserRepository.");
		System.out.println("so i have commented out @service of UserService class to use in memory authetication");
	}
}
