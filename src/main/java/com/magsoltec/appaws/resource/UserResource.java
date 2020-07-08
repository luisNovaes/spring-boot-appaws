package com.magsoltec.appaws.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.dto.USerLoginDto;
import com.magsoltec.appaws.dto.UserLoginResponsedto;
import com.magsoltec.appaws.dto.UserSaveDto;
import com.magsoltec.appaws.dto.UserUpdateDto;
import com.magsoltec.appaws.dto.UserUpdateRoleDto;
import com.magsoltec.appaws.model.PageModel;
import com.magsoltec.appaws.model.PageRequestModel;
import com.magsoltec.appaws.security.AccessManager;
import com.magsoltec.appaws.security.JwtManager;
import com.magsoltec.appaws.service.RequestService;
import com.magsoltec.appaws.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtManager jwtManager;

	@Autowired
	private AccessManager accessManager;

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSaveDto userDto) {
		User userToSave = userDto.transformToUser();
		User createUser = userService.save(userToSave);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}

	@PreAuthorize("@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateDto userDto) {
		User user = userDto.transformToUser();
		user.setId(id);
		User updateUser = userService.update(user);
		return ResponseEntity.ok(updateUser);

	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		return ResponseEntity.ok(user);

	}

	@GetMapping
	public ResponseEntity<PageModel<User>> listAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<User> pm = userService.listAllOnLazyModel(pr);

		return ResponseEntity.ok(pm);

	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponsedto> login(@RequestBody @Valid USerLoginDto user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
				user.getPassword());
		Authentication auth = authManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(auth);

		org.springframework.security.core.userdetails.User userSpring = (org.springframework.security.core.userdetails.User) auth
				.getPrincipal();

		String email = userSpring.getUsername();
		List<String> roles = userSpring.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(jwtManager.createToken(email, roles));
	}

	@GetMapping("/{id}/request")
	public ResponseEntity<PageModel<Request>> listAllRequestsById(@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pr = new PageRequestModel(page, size);

		PageModel<Request> pm = requestService.listAllByOwnerIdLazyModel(id, pr);
		return ResponseEntity.ok(pm);
	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PatchMapping("/role/{id}")
	public ResponseEntity<?> updateRole(@PathVariable(name = "id") Long id,
			@RequestBody @Valid UserUpdateRoleDto userDto) {

		User user = new User();
		user.setId(id);
		user.setRole(userDto.getRole());

		userService.updateRole(user);

		return ResponseEntity.ok().build();

	}

}
