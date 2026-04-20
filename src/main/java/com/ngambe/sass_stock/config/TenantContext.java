package com.ngambe.sass_stock.config;

public class TenantContext {
	
	
	private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
	
	public static void setCurrentTenant(final String tenant) {
		CURRENT_TENANT.set(tenant);
	}
	
	public static String getCurremtTenant() {
		return CURRENT_TENANT.get();
	}
	
	public static void clear() {
		CURRENT_TENANT.remove();
	}

}
