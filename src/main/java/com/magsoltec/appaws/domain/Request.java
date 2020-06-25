package com.magsoltec.appaws.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.magsoltec.appaws.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Request {

	private Long id;
	private String subject;
	private String description;
	private Date creationDate;
	private RequestStage stage;
	private User user;
	private List<RequestState> stages = new ArrayList<RequestState>();

}
