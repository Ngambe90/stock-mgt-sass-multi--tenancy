package com.ngambe.sass_stock.mapper;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.dto.request.UserRequest;
import com.ngambe.sass_stock.dto.response.UserResponse;
import com.ngambe.sass_stock.entities.User;

@Component
public class UserMapper {
	
	
	
	public User toEntity(final UserRequest request) {
		return User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(request.getPassword())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.role(request.getRole())
				.build();
	}

	public UserResponse toResponse(User user) {
		
		return UserResponse.builder()
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.role(user.getRole())
				.build();
	}

}
