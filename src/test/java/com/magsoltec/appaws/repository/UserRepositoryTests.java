package com.magsoltec.appaws.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.domain.enums.Role;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

//	@Test
//	@Order(1)
	public void Asavetest() {

		User user = new User();

		user.setName("Luis");
		user.setEmail("luis@gmail.com");
		user.setPassword("ma2020");
		user.setRole(Role.ADMINISTRATOR);

		User createUser = userRepository.save(user);

		assertThat(createUser.getId()).isEqualTo(1L);

	}

//	@Test
//	@Order(2)
	public void updateTest() {

		Optional<User> userPresente = userRepository.findById(1L);

		User user = userPresente.get();
		user.setName("Luis Magno");
		user.setEmail("luis@gmail.com");
		user.setPassword("ma2020");
		user.setRole(Role.ADMINISTRATOR);
		User updateUser = userRepository.save(user);
		assertThat(updateUser.getName()).isEqualTo("Luis Magno");

	}

//	@Test
//	@Order(3)
	public void getByIdtest() {

		Optional<User> result = userRepository.findById(1L);
		User user = result.get();

		assertThat(user.getPassword()).isEqualTo("ma2020");

	}

//	@Test
//	@Order(4)
	public void listTest() {
		List<User> user = userRepository.findAll();

		assertThat(user.size()).isEqualTo(1);

	}

//	@Test
//	@Order(5)
	public void loginTest() {

		Optional<User> result = userRepository.login("luis@gmail.com", "ma2020");

		User loggedUser = result.get();

		assertThat(loggedUser.getId()).isEqualTo(1L);

	}

	@Test
	//@Order(6)
	public void updateRoleTest() {
		int affectedRows = userRepository.updateRole(6L, Role.ADMINISTRATOR);
		assertThat(affectedRows).isEqualTo(1);

	}

}
