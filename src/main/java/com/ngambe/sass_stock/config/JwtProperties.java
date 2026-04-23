package com.ngambe.sass_stock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

		 private String privateKeyPath;
		 private String publicKeyPath;
		 private String accessTokenExpiration;
}
