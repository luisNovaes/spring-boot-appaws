package com.magsoltec.appaws.dto;

import javax.validation.constraints.NotNull;

import com.magsoltec.appaws.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDto {

	@NotNull(message = "Role required")
	private Role role;

}
