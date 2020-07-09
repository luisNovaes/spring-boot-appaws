package com.magsoltec.appaws.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.RequestFile;
import com.magsoltec.appaws.domain.RequestStage;
import com.magsoltec.appaws.dto.RequestSavedto;
import com.magsoltec.appaws.dto.RequestUpdateDto;
import com.magsoltec.appaws.model.PageModel;
import com.magsoltec.appaws.model.PageRequestModel;
import com.magsoltec.appaws.security.AccessManager;
import com.magsoltec.appaws.service.RequestFileService;
import com.magsoltec.appaws.service.RequestService;
import com.magsoltec.appaws.service.RequestStageService;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

	@Autowired
	private RequestService requestService;

	@Autowired
	private RequestStageService requestStageService;

	@Autowired
	private AccessManager accessManager;

	@Autowired
	private RequestFileService fileService;

	@PostMapping
	public ResponseEntity<Request> save(@RequestBody @Valid RequestSavedto requestDto) {
		Request request = requestDto.transformToRequest();
		Request createRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createRequest);

	}

	@PreAuthorize("@accessManager.isRequestOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable(name = "id") Long id,
			@RequestBody @Valid RequestUpdateDto requestDto) {

		Request request = requestDto.transformToRequest();
		request.setId(id);
		Request updateRequest = requestService.update(request);
		return ResponseEntity.ok(updateRequest);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable(name = "id") Long id) {
		Request request = requestService.getById(id);
		return ResponseEntity.ok(request);

	}

	@GetMapping
	public ResponseEntity<PageModel<Request>> listAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<Request> pm = requestService.listAllOnLazyModel(pr);

		return ResponseEntity.ok(pm);

	}

	@GetMapping("/{id}/request-stages")
	public ResponseEntity<PageModel<RequestStage>> listAllStageById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pr = new PageRequestModel(page, size);

		PageModel<RequestStage> pm = requestStageService.listAllByOwnerIdLazyModel(id, pr);
		return ResponseEntity.ok(pm);
	}

	@GetMapping("/{id}/files")
	public ResponseEntity<PageModel<RequestFile>> listAllFilesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<RequestFile> pm = fileService.listAllByRequestId(id, pr);
		
		return ResponseEntity.ok(pm);
	}

	@PostMapping("/{id}/files")
	public ResponseEntity<List<RequestFile>> upload(
			@RequestParam("files") MultipartFile[] files,
			@PathVariable(name = "id") Long id) {
		
		List<RequestFile> requestFiles = fileService.upload(id, files);

		return ResponseEntity.status(HttpStatus.CREATED).body(requestFiles);
	}

}
