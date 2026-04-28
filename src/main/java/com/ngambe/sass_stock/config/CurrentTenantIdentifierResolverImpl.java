package com.ngambe.sass_stock.config;

import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component 
@Slf4j
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String>,HibernatePropertiesCustomizer{
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.putIfAbsent(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
		
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		final String schemaName= TenantContext.getCurrentSchema();
		log.trace("Resolving Current tenant schema identifier : {}",schemaName);
		if(schemaName == null) {
			return "public";
		}
		return schemaName;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		
		return true;
	}

}
