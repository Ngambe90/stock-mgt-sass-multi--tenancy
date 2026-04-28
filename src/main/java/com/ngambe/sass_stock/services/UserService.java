package com.ngambe.sass_stock.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.UserRequest;
import com.ngambe.sass_stock.dto.response.UserResponse;

public interface UserService extends UserDetailsService{
	
	void createUser(final UserRequest request);
	
	void updateUser(final String userId, final UserRequest request);
	
	void deteleUser(final String userId);
	
	UserResponse getUserById(final String userId);
	
	PageResponse<UserResponse> getAllUsers(final int page, final int size);
	
	void enableUser(final String userId);
	
	void desableUser(final String userId);
	
	

}
