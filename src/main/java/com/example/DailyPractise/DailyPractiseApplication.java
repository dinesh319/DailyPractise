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

		System.out.println("There are two ways to restrict access:\n" +
				"\n" +
				"Role-Based (hasRole() / hasAnyRole())\n" +
				"\n" +
				"Requires prefixing roles with ROLE_ in Spring Security.\n" +
				"\n" +
				"Example:\n" +
				"\n" +
				".requestMatchers(HttpMethod.POST , \"/posts/**\").hasAnyRole(ADMIN.name())\n" +
				"Would only allow users with the role \"ROLE_ADMIN\".\n" +
				"\n" +
				"Authority-Based (hasAuthority() / hasAnyAuthority())\n" +
				"\n" +
				"Uses the exact authority name without any prefix.\n" +
				"\n" +
				"\n");
	}
}
