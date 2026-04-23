package com.ngambe.sass_stock.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.dto.request.LoginRequest;
import com.ngambe.sass_stock.dto.response.LoginResponse;
import com.ngambe.sass_stock.entities.User;
import com.ngambe.sass_stock.security.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;
	@Override
	public LoginResponse login(LoginRequest request) {
		final Authentication authentication= this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
		
		final User user = (User)authentication.getPrincipal();
		
		final String token = this.jwtTokenService.generateAccessToken(user.getTenantId(), user.getId(), user.getRole().name());
		final String tokenType = "Bearer";
		
		return LoginResponse.builder()
				.accessToken(token)
				.tokenType(tokenType)
				.build();
	}

}
