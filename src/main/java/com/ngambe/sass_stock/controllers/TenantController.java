package com.ngambe.sass_stock.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.response.TenantResponse;
import com.ngambe.sass_stock.services.TenantService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/tenants")
@Tag(name="Tenant", description = "Tenant API")
public class TenantController {

	private final TenantService tenantService;
	
	@PostMapping("/approuve/{tenant-id}")
	public ResponseEntity<Void>approuve(@PathVariable("tenant-id") final String tenantId){
		this.tenantService.approuveTenant(tenantId);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/activate/{tenant-id}")
	public ResponseEntity<Void>activate(@PathVariable("tenant-id") final String tenantId){
		this.tenantService.activedTenant(tenantId);
		return ResponseEntity.ok().build();
	}
	@PatchMapping("/deactivate/{tenant-id}")
	public ResponseEntity<Void>deactivate(@PathVariable("tenant-id") final String tenantId){
		this.tenantService.deactivedTenant(tenantId);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/suspend/{tenant-id}")
	public ResponseEntity<Void>suspendTenant(@PathVariable("tenant-id") final String tenantId){
		this.tenantService.suspendTenant(tenantId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<TenantResponse>>findAllTenants(
			@RequestParam(defaultValue = "0")
			final int page,
			@RequestParam(defaultValue = "10")
			final int size){
		
		return ResponseEntity.ok(this.tenantService.findAll(page, size));
	}
}
