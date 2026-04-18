package com.ngambe.sass_stock.mapper;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.dto.request.StockMvtDtoRequest;
import com.ngambe.sass_stock.dto.response.StockMvtDtoResponse;
import com.ngambe.sass_stock.entities.Product;
import com.ngambe.sass_stock.entities.StockMvt;

@Component
public class StockMvtMapper {

	
	public StockMvt toEntity(final StockMvtDtoRequest request) {
		return StockMvt.builder()
				.typeMvt(request.getTypeMvt())
				.dateMvt(request.getDateMvt())
				.qunatity(request.getQunatity())
				.comment(request.getComment())
				.product(Product.builder().id(request.getProductId()).build())
				.build();
	}
	
	public StockMvtDtoResponse toResponse(final StockMvt response) {
		return StockMvtDtoResponse.builder()
				.typeMvt(response.getTypeMvt())
				.dateMvt(response.getDateMvt())
				.qunatity(response.getQunatity())
				.comment(response.getComment())
				.build();
	}
}
