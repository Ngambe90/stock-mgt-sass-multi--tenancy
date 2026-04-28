package com.ngambe.sass_stock.dto.response;

import java.time.LocalDateTime;

import com.ngambe.sass_stock.enumeration.TenantStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class TenantResponse {
	private String id;
	private String companyName;
	
	private String companyCode;
		
	private String email;

	private String adminFullName;
	private String adminEmail;

	private String adminUsername;
	private String adminPassword;
	private TenantStatus status;
	
	private LocalDateTime createdAt;

}
