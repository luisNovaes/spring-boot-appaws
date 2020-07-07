package com.magsoltec.appaws.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.repository.UserRepository;
import com.magsoltec.appaws.service.RequestService;

import javassist.NotFoundException;

@Component("accessManager")
public class AccessManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestService requestService;

	public boolean isOwner(Long id) throws NotFoundException {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);

		if (!result.isPresent())
			throw new NotFoundException("There are not user with e-mail = " + email);

		User user = result.get();

		return user.getId() == id;
	}

	public boolean isRequestOwner(Long id) throws NotFoundException {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);

		if (!result.isPresent())
			throw new NotFoundException("There are not user with e-mail = " + email);

		User user = result.get();

		Request request = requestService.getById(id);

		return user.getId() == request.getOwner().getId();

	}

}
