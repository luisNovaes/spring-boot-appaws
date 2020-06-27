package com.magsoltec.appaws.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.domain.enums.RequestState;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RequestRepositoryTests {

	@Autowired
	private RequestRepository requestRepository;

	@Test
	@Order(1)
	public void saveTest() {

		User owner = new User();
		owner.setId(1L);

		Request request = new Request();

		request.setSubject("Novo Laptop HP");
		request.setDescription("Pretendo obter um Laptop HP");
		request.setCreationDate(new Date());
		request.setState(RequestState.OPEN);
		request.setOwner(owner);
		request.setStages(null);

		Request createdrequest = requestRepository.save(request);

		assertThat(createdrequest.getId()).isEqualTo(1L);

	}

	@Test
	@Order(2)
	public void updateTest() {

		User owner = new User();
		owner.setId(1L);

		Optional<Request> userPresente = requestRepository.findById(1L);

		Request request = userPresente.get();

		request.setSubject("Novo Laptop HP");
		request.setDescription("Pretendo obter um Laptop HP, de RAM 16GB");
		request.setCreationDate(null);
		request.setState(RequestState.OPEN);
		request.setOwner(owner);
		request.setStages(null);

		Request updaterequest = requestRepository.save(request);

		assertThat(updaterequest.getDescription()).isEqualTo("Pretendo obter um Laptop HP, de RAM 16GB");

	}

	@Test
	@Order(3)
	public void getByIdTest() {

		Optional<Request> result = requestRepository.findById(1L);
		Request request = result.get();

		assertThat(request.getSubject()).isEqualTo("Novo Laptop HP");

	}

	@Test
	@Order(4)
	public void listTest() {

		List<Request> requests = requestRepository.findAll();

		assertThat(requests.size()).isEqualTo(1);

	}

	@Test
	@Order(5)
	public void listByOwnerIdTest() {

		List<Request> requests = requestRepository.findAllByOwnerId(1L);

		assertThat(requests.size()).isEqualTo(1);

	}

	@Test
	public void updateStatusTest() {

		int affectedRows = requestRepository.updateStatus(1l, RequestState.IN_PROGRESS);
		assertThat(affectedRows).isEqualTo(1);

	}

}
