package com.ngambe.sass_stock.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.auth.service.AuthenticationService;
import com.ngambe.sass_stock.dto.request.LoginRequest;
import com.ngambe.sass_stock.dto.response.LoginResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest request){
		
		final LoginResponse response = this.authenticationService.login(request);
		
		return ResponseEntity.ok(response);
	}
}
