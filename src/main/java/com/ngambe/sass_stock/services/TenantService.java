package com.ngambe.sass_stock.services;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.TenantRegisterRequest;
import com.ngambe.sass_stock.dto.response.TenantResponse;

public interface TenantService {

	void TenantRegister(final TenantRegisterRequest request);
	
	void approuveTenant(final String tenantId);
	
	void activedTenant(final String tenantId);
	
	void deactivedTenant(final String tenantId);
	
	void suspendTenant(final String tenantId);
	
	PageResponse<TenantResponse> findAll(final int page, final int size);
}
