package com.ngambe.sass_stock.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.dto.request.TenantRegisterRequest;
import com.ngambe.sass_stock.dto.response.TenantResponse;
import com.ngambe.sass_stock.entities.Tenant;

@Component
public class TenantMapper {
	
	public Tenant toEntity(final TenantRegisterRequest request) {
		return Tenant.builder()
				.companyName(request.getCompanyName())
				.companyCode(request.getCompanyCode())
				.createdAt(LocalDateTime.now())
				.email(request.getEmail())
				.adminFullName(request.getAdminFullName())
				.adminUsername(request.getAdminUsername())
				.adminEmail(request.getAdminEmail())
				.build();
	}
	
	public TenantResponse toResponse(final Tenant response) {
		return TenantResponse.builder()
				.id(response.getId())
				.companyName(response.getCompanyName())
				.companyCode(response.getCompanyCode())
				.createdAt(response.getCreatedAt())
				.email(response.getEmail())
				.adminFullName(response.getAdminFullName())
				.adminUsername(response.getAdminUsername())
				.adminEmail(response.getAdminEmail())
				.status(response.getStatus())
				.build();
	}

}
