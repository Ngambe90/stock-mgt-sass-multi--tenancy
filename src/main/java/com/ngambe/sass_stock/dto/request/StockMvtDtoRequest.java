package com.ngambe.sass_stock.dto.request;

import java.time.LocalDateTime;

import com.ngambe.sass_stock.entities.enumeration.TypeMvt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class StockMvtDtoRequest {
	
	@NotBlank(message = "Type of mouvement should not be empty")
	private TypeMvt typeMvt;
	
	@Positive(message = "Quantity should be a positive number")
	private Integer qunatity;
	
	@NotNull(message = "Date of mouvement should not be empty")
	@PastOrPresent(message = "Date of mouvement should be in the past or present")
	private LocalDateTime dateMvt;
	private String comment;
	
	@NotBlank(message = "Product ID should not be empty")
	private String productId;

}
