package com.ngambe.sass_stock.dto.response;

import java.math.BigDecimal;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDtoResponse {

	private String id;
	private String product_name;
	
	private String reference;
	
	private String description;
	
	private BigDecimal price;
	
	private Integer alerteThresholds;
	
	private String categoryName;
	private int availableQuantity;
}
