package com.ngambe.sass_stock.dto.response;

import com.ngambe.sass_stock.enumeration.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder

public class UserResponse {

	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private UserRole role;
		
}
