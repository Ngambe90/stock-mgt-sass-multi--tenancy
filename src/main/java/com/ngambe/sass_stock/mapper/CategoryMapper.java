package com.ngambe.sass_stock.mapper;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.dto.request.CategorieDtoRequest;
import com.ngambe.sass_stock.dto.response.CategorieDtoResponse;
import com.ngambe.sass_stock.entities.Categorie;

@Component
public class CategoryMapper {

	
	public Categorie toEntity(final CategorieDtoRequest request) {
		return Categorie.builder()
				.categorieName(request.getCategorieName())
				.description(request.getDescription())
				.build();
	}
	
	public CategorieDtoResponse toResponse(final Categorie response) {
		final int nbProducts =0;// response.getProducts()==null ? 0 : response.getProducts().size();
		return CategorieDtoResponse.builder()
				.categorieName(response.getCategorieName())
				.description(response.getDescription())
				.nbProducts(nbProducts)
				.build();
	}
}
