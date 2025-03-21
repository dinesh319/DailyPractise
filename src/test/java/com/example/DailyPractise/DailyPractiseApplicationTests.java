package com.example.DailyPractise;

import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DailyPractiseApplicationTests {


	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		// generate token
		UserEntity user = new UserEntity(1L,"dino@gmail.com","dino");
		String token = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}



}
