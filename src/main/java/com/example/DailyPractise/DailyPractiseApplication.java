package com.example.DailyPractise;

import com.example.DailyPractise.basic.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyPractiseApplication implements CommandLineRunner {

	@Autowired   // need this to inject the dependency in this bean
	Apple apple ;
	public static void main(String[] args) {
		SpringApplication.run(DailyPractiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("welcome dinesh hanumanthu");

		apple.eat();


	}
}
