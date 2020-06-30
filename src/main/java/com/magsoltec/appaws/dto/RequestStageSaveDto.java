package com.magsoltec.appaws.dto;

import javax.validation.constraints.NotNull;

import com.magsoltec.appaws.domain.Request;
import com.magsoltec.appaws.domain.RequestStage;
import com.magsoltec.appaws.domain.User;
import com.magsoltec.appaws.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestStageSaveDto {

	private String description;

	@NotNull(message = "State required")
	private RequestState state;

	@NotNull(message = "Request required")
	private Request request;

	@NotNull(message = "Owner required")
	private User owner;

	public RequestStage transformeToRequestStage() {
		RequestStage stage = new RequestStage(null, this.description, null, this.state, this.request, this.owner);
		return stage;

	}
}
