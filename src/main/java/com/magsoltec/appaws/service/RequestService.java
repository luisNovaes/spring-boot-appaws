package com.magsoltec.appaws.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.enums.RequestState;
import com.magsoltec.appaws.exception.NotFoundException;
import com.magsoltec.appaws.model.PageModel;
import com.magsoltec.appaws.model.PageRequestModel;
import com.magsoltec.appaws.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	public Request save(Request request) {

		request.setState(RequestState.OPEN);
		request.setCreationDate(new Date());

		Request createRequest = requestRepository.save(request);

		return createRequest;
	}

	public Request update(Request request) {
		Request updateRequest = requestRepository.save(request);
		return updateRequest;
	}

	public Request getById(Long id) {

		Optional<Request> result = requestRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not request with id = " + id));

	}

	public List<Request> listAll() {
		List<Request> requests = requestRepository.findAll();
		return requests;

	}

	public PageModel<Request> listAllOnLazyModel(PageRequestModel pr) {

		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> page = requestRepository.findAll(pageable);

		PageModel<Request> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
				page.getContent());
		return pm;

	}

	public List<Request> listAllByOwnerId(Long ownerId) {
		List<Request> requests = requestRepository.findAllByOwnerId(ownerId);
		return requests;

	}

	public PageModel<Request> listAllByOwnerIdLazyModel(Long ownerId, PageRequestModel pr) {

		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);

		PageModel<Request> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
				page.getContent());

		return pm;

	}

}
