package com.ngambe.sass_stock.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CategorieDtoRequest {
	
	@NotNull(message="Category name should not be empty")
	@Size(min=3, max=255,message="Category name should  be between 3 and 255 characters" )
	private String categorieName;

	private String description;
		
}
