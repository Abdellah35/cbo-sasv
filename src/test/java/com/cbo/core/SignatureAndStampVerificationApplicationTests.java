package com.cbo.core;

import com.cbo.core.model.User;
import com.cbo.core.repo.UserRepository;
import com.cbo.core.service.UserService;
import io.swagger.annotations.Authorization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignatureAndStampVerificationApplicationTests {

/*
	UserService userService;

	UserRepository userRepository;

	@Test
	public void getUsersTest(){

		when(userRepository.findAll()).thenReturn(Stream.of(
				new User(10L, "abd7", "abd@eml.com","abd5"), new User(106L, "abd75", "abd5@eml.com","abd5")).collect(Collectors.toList())
		);

		assertEquals(2, userService.findAllUser().size());

	}*/


}
