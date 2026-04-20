package com.ngambe.sass_stock.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDtoRequest {
	
	@NotBlank(message = "product name should not be empty")
	@Size(min=3, max=255, message = "product name should  be between 3 and 255 characters")
	private String product_name;
	
	@NotBlank(message = "product reference should not be empty")
	@Size(min=3, max=255, message = "product reference should  be between 3 and 255 characters")
	private String reference;
	
	private String description;
	
	@Positive(message = "price should be a positive number")
	private BigDecimal price;
	
	@Positive(message = "Alerte Thresholds should be a positive number")
	private Integer alerteThresholds;
	
	@NotBlank(message = "Category  should not be empty")
	private String categoryId;

}
