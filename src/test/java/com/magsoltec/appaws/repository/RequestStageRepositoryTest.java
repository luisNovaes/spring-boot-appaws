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
import com.magsoltec.appaws.domain.RequestStage;
import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.domain.enums.RequestState;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RequestStageRepositoryTest {

	@Autowired
	RequestStageRepository requestStageRepository;

	@Test
	@Order(1)
	public void saveTest() {

		User owner = new User();
		owner.setId(1L);

		Request request = new Request();
		request.setId(1l);

		RequestStage stage = new RequestStage();

		stage.setDescription("Foi comprado um novo laptop de marca HD e 16 GB de RAM");
		stage.setRealizationDate(new Date());
		stage.setState(RequestState.CLOSED);
		stage.setRequest(request);
		stage.setOwner(owner);

		RequestStage createStage = requestStageRepository.save(stage);

		assertThat(createStage.getId()).isEqualTo(1L);

	}

	@Test
	@Order(2)
	public void getByIdTest() {

		Optional<RequestStage> result = requestStageRepository.findById(1L);
		RequestStage stage = result.get();

		assertThat(stage.getDescription()).isEqualTo("Foi comprado um novo laptop de marca HD e 16 GB de RAM");

	}

	@Test
	@Order(3)
	public void listByRequestIdTest() {
		List<RequestStage> stages = requestStageRepository.findAll();

		assertThat(stages.size()).isEqualTo(1);

	}

}
