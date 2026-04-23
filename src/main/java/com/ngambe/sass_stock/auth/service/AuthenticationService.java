package com.ngambe.sass_stock.auth.service;

import com.ngambe.sass_stock.dto.request.LoginRequest;
import com.ngambe.sass_stock.dto.response.LoginResponse;

public interface AuthenticationService {

	
	LoginResponse login(LoginRequest request);
}
