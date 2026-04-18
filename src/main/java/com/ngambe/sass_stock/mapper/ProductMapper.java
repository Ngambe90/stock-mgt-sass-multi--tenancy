package com.ngambe.sass_stock.mapper;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.dto.request.ProductDtoRequest;
import com.ngambe.sass_stock.dto.response.ProductDtoResponse;
import com.ngambe.sass_stock.entities.Categorie;
import com.ngambe.sass_stock.entities.Product;

@Component
public class ProductMapper {

	
	public Product toEntity(final ProductDtoRequest request) {
		return Product.builder()
				.product_name(request.getProduct_name())
				.reference(request.getReference())
				.description(request.getDescription())
				.price(request.getPrice())
				.alerteThresholds(request.getAlerteThresholds())
				.categorie(Categorie.builder().id(request.getCategoryId()).build())
				.build();
	}
	
	public ProductDtoResponse toResponse(final Product response) {
		return ProductDtoResponse.builder()
				.product_name(response.getProduct_name())
				.reference(response.getReference())
				.description(response.getDescription())
				.alerteThresholds(response.getAlerteThresholds())
				.categoryName(response.getCategorie().getCategorieName())
				.build();
	}
}
