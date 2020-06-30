package com.magsoltec.appaws.dto;

import com.magsoltec.appaws.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleDto {
	private Role role;

}
