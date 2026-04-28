package com.ngambe.sass_stock.auth;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.auth.service.AuthenticationService;
import com.ngambe.sass_stock.dto.request.LoginRequest;
import com.ngambe.sass_stock.dto.request.TenantRegisterRequest;
import com.ngambe.sass_stock.dto.response.LoginResponse;
import com.ngambe.sass_stock.services.TenantService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor @Slf4j 
@Tag(name="Authentication", description = "Authentication API")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final TenantService tenantService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest request){
		
		final LoginResponse response = this.authenticationService.login(request);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid  @RequestBody final TenantRegisterRequest request){
		 
		this.tenantService.TenantRegister(request);
		
		return ResponseEntity.ok().build();
	}
	
}
