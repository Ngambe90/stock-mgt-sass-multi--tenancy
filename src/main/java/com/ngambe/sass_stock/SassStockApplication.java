package com.ngambe.sass_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SassStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SassStockApplication.class, args);
	}

}
