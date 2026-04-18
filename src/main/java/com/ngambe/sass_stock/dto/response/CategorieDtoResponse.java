package com.ngambe.sass_stock.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CategorieDtoResponse {

	private String id;
	private String categorieName;

	private String description;
	private int nbProducts;
}
