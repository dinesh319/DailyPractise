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
		System.out.println("remember you have to create locally");
		System.out.println("if you did not create a database then create one");

		/*
				create database dino;
				create USER dino WITH PASSWORD 'dino';
				ALTER ROLE dino SET client_encoding TO 'utf8';
				ALTER ROLE dino SET default_transaction_isolation TO 'read committed';
				ALTER ROLE dino SET timezone TO 'UTC';
				GRANT ALL PRIVILEGES ON DATABASE dino TO dino;
				ALTER USER dino WITH SUPERUSER;
				ALTER USER dino WITH createrole;
				ALTER USER dino WITH createdb;
				ALTER USER dino WITH replication;
				ALTER USER dino WITH bypassrls;
				ALTER DATABASE dino OWNER TO dino;


		 */
	}
}
