package com.magsoltec.appaws.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.magsoltec.appaws.domain.RequestStage;
import com.magsoltec.appaws.domain.enums.RequestState;
import com.magsoltec.appaws.exception.NotFoundException;
import com.magsoltec.appaws.model.PageModel;
import com.magsoltec.appaws.model.PageRequestModel;
import com.magsoltec.appaws.repository.RequestRepository;
import com.magsoltec.appaws.repository.RequestStageRepository;

@Service
public class RequestStageService {

	@Autowired
	private RequestStageRepository requestStageRepository;

	@Autowired
	private RequestRepository requestRepository;

	public RequestStage save(RequestStage stage) {

		stage.setRealizationDate(new Date());

		RequestStage createStage = requestStageRepository.save(stage);

		Long requestId = stage.getRequest().getId();
		RequestState state = stage.getState();

		requestRepository.updateStatus(requestId, state);

		return createStage;

	}

	public RequestStage getById(Long id) {

		Optional<RequestStage> result = requestStageRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not request stage with id = " + id));
	}

	public List<RequestStage> listAllByRequestId(Long requestId) {

		List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
		return stages;
	}

	public PageModel<RequestStage> listAllByOwnerIdLazyModel(Long requestId, PageRequestModel pr) {

		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);

		PageModel<RequestStage> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(),
				page.getTotalPages(), page.getContent());

		return pm;

	}

}
