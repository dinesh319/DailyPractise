package com.example.DailyPractise;

import com.example.DailyPractise.clients.UserService;
import com.example.DailyPractise.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DailyPractiseApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void getAllUsersUsingRestClient(){
		List<?> data = userService.getAllUsers();
		System.out.println(data);
	}

	@Test
	void getUserByIdUsingRestClient(){
		UserDto user = userService.getUserById(1L);
		System.out.println(user.toString());
	}


	@Test
	void createUserByRestCliert(){
		UserDto userDto = new UserDto("dino@gmail.com","dino","hanu","https://reqres.in/img/faces/2-image.jpg");
		userService.createUser(userDto);
	}

	@Test
	void deleteUserById(){
		userService.deleteUser(1L);
	}


}
