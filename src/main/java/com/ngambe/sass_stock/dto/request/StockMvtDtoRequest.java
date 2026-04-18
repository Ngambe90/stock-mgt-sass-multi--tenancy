package com.ngambe.sass_stock.dto.request;

import java.time.LocalDateTime;

import com.ngambe.sass_stock.entities.enumeration.TypeMvt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class StockMvtDtoRequest {
	
	private String id;
	private TypeMvt typeMvt;
	private Integer qunatity;
	private LocalDateTime dateMvt;
	private String comment;
	private String productId;

}
