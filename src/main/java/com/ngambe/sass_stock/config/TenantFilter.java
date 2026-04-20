package com.ngambe.sass_stock.config;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter{

	private static final String TENANT_HEADER="X-tenant-ID";
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response =(HttpServletResponse) servletResponse;
		
		final String tenantId=resolverTenant(request);
		
		if(tenantId == null || tenantId.isBlank()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.getWriter().write(
					"{\"error\": \"Tenant ID is missing in the request header, please add the header X-tenant-ID\"}"
					);
			return;
		}
		try {
			// stocker le tenant dans le threadLocal
			
			TenantContext.setCurrentTenant(tenantId);
			filterChain.doFilter(servletRequest, servletResponse);
		}finally {
			TenantContext.clear();
		}
		
	}
	
	private String resolverTenant(final HttpServletRequest request) {
		
		final String tenantId = request.getHeader(TENANT_HEADER);
		if(tenantId !=null && !tenantId.isBlank()) {
			return tenantId.trim().toLowerCase();
		}
		return null;
	}

}
