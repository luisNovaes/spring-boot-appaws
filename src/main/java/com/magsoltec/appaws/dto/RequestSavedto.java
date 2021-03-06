package com.magsoltec.appaws.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.RequestStage;
import com.magsoltec.appaws.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestSavedto {

	@NotBlank(message = "Subject required")
	private String subject;

	private String description;

	@NotNull(message = "Owner required")
	private User owner;

	private List<RequestStage> stages = new ArrayList<RequestStage>();

	public Request transformToRequest() {
		Request request = new Request(null, this.subject, this.description, null, null, this.owner, this.stages, null);
		return request;

	}

}
