package com.ngambe.sass_stock.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CategorieDtoRequest {
	
	private String categorieName;

	private String description;
		
}
