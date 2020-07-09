package com.magsoltec.appaws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.magsoltec.appaws.domain.RequestFile;
import com.magsoltec.appaws.model.PageModel;
import com.magsoltec.appaws.model.PageRequestModel;
import com.magsoltec.appaws.repository.RequestFileRepository;

@Service
public class RequestFileService {

	@Autowired
	private RequestFileRepository fileRepository;

//	@Autowired
//	private S3Service s3Service;
//
//	public List<RequestFile> upload(Long requestId, MultipartFile[] files) {
//		List<UploadedFileModel> uploadedFiles = s3Service.upload(files);
//		List<RequestFile> requestFiles = new ArrayList<RequestFile>();
//
//		uploadedFiles.forEach(uploadedFile -> {
//			RequestFile file = new RequestFile();
//			file.setName(uploadedFile.getName());
//			file.setLocation(uploadedFile.getLocation());
//
//			Request request = new Request();
//			request.setId(requestId);
//
//			file.setRequest(request);
//
//			requestFiles.add(file);
//		});
//
//		return fileRepository.saveAll(requestFiles);
//	}

	public PageModel<RequestFile> listAllByRequestId(Long requestId, PageRequestModel prm) {

		Pageable pageable = PageRequest.of(prm.getPage(), prm.getSize());

		Page<RequestFile> page = fileRepository.findAllByRequestId(requestId, pageable);

		PageModel<RequestFile> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(),
				page.getContent());

		return pm;
	}
}
