package com.ngambe.sass_stock.dto.request;


import com.ngambe.sass_stock.enumeration.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class UserRequest {
	
	@NotBlank(message = "username should not be empty")
	private String username;
	
	@NotBlank(message = "email should not be empty")
	private String email;
	
	@NotBlank(message = "password should not be empty")
	private String password;
	
	@NotBlank(message = "first name should not be empty")
	private String firstName;
	
	@NotBlank(message = "last name should not be empty")
	private String lastName;
	
	@NotNull(message = "role should not be empty")
	private UserRole role;
		
}
