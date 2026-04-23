package com.ngambe.sass_stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class BeansConfigs {

	
	@Bean
	AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) {
		
		return authenticationConfiguration.getAuthenticationManager();
	}
}
