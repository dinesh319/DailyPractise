package com.example.DailyPractise;

import com.example.DailyPractise.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyPractiseApplication implements CommandLineRunner {

	@Autowired
	private DataService dataService;

	@Value("${environment}")
	private String env;

	public static void main(String[] args) {
		SpringApplication.run(DailyPractiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("welcome dinesh hanumanthu");

		dataService.getData();
		System.out.println(env);

	}
}
