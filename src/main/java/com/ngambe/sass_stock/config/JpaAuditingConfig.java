package com.ngambe.sass_stock.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@Configuration
public class JpaAuditingConfig {

	
	@Bean
	AuditorAware<String>auditorProvider(){
		
		return new AuditorAwareImpl();
		
	}
	
	public static class AuditorAwareImpl implements AuditorAware<String>{

		@Override
		public Optional<String> getCurrentAuditor() {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication == null || !authentication.isAuthenticated()|| authentication.getPrincipal().equals("anonymousUser")) {
				return Optional.empty();
			}
			
			if(authentication.getPrincipal() !=null) {
				return Optional.of((String)authentication.getPrincipal()) ;
			}
			return Optional.empty();
		}
		
		
	}
}
