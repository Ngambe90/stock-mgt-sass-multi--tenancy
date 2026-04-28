package com.ngambe.sass_stock.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.UserRequest;
import com.ngambe.sass_stock.dto.response.UserResponse;
import com.ngambe.sass_stock.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	
	@PostMapping
	@PreAuthorize("hasRole('COMPANY_ADMIN')")
	public ResponseEntity<Void> creatUser(@Valid @RequestBody final UserRequest request){
		this.userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('COMPANY_ADMIN','ADMINISTRATOR')")
	public ResponseEntity<PageResponse<UserResponse>> getAllUsers(@RequestParam(value = "0") final int page, @RequestParam(value = "10")final int size){
		final PageResponse<UserResponse> response= this.userService.getAllUsers(page, size);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{user-id}")
	@PreAuthorize("hasAnyRole('COMPANY_ADMIN','ADMINISTRATOR')")
	public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "user-id")final String userId){
		final UserResponse response= this.userService.getUserById(userId);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{user-id}")
	@PreAuthorize("hasAnyRole('COMPANY_ADMIN')")
	public ResponseEntity<Void> updatetUser(@PathVariable(name = "user-id")final String userId, @Valid @RequestBody final UserRequest request){
		this.userService.updateUser(userId, request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	@DeleteMapping("/{user-id}")
	@PreAuthorize("hasRole('COMPANY_ADMIN')")
	public ResponseEntity<Void> deleteUser(@PathVariable(name = "user-id") final String userId){
		this.userService.deteleUser(userId);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("/{user-id}/enable")
	@PreAuthorize("hasRole('COMPANY_ADMIN')")
	public ResponseEntity<Void> enableUser(@PathVariable(name = "user-id") final String userId){
		this.userService.enableUser(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	@PutMapping("/{user-id}/disable")
	@PreAuthorize("hasRole('COMPANY_ADMIN')")
	@Operation(summary = "Disable User",description = "Disable a user account (prevents login)")
	public ResponseEntity<Void> disableUser(@PathVariable(name = "user-id") final String userId){
		this.userService.desableUser(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
