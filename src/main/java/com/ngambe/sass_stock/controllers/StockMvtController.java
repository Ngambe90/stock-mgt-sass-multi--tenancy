package com.ngambe.sass_stock.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.StockMvtDtoRequest;
import com.ngambe.sass_stock.dto.response.CategorieDtoResponse;
import com.ngambe.sass_stock.dto.response.StockMvtDtoResponse;
import com.ngambe.sass_stock.services.StockMvtService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor @RequestMapping("api/v1/stockmvts")
@Tag(name="Stock Mvt", description = "Stock Mvt API")
public class StockMvtController {

	private final StockMvtService mvtService;
	
	@PostMapping
	public ResponseEntity<Void> createStockMvt(@RequestBody @ Valid final StockMvtDtoRequest request){
		this.mvtService.create(request);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{stockmvt-id}")
	public ResponseEntity<Void> updateStockMvt(@RequestBody @ Valid final 
			StockMvtDtoRequest request, 
			@PathVariable("stockmvt-id")
			@NotNull(message = "stockmvt ID can not be null")
			String id){
		this.mvtService.update(id, request);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/{stockmvt-id}")
	public ResponseEntity<CategorieDtoResponse> getStockMvtById (@PathVariable("stockmvt-id") @NotNull(message = "Product ID can not be null")String id){
		this.mvtService.findById(id);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	public ResponseEntity<PageResponse<StockMvtDtoResponse>> getAllStockMvts (
			@RequestParam( defaultValue = "0")
			final int page, 
			@RequestParam( defaultValue = "10")
			final int size){
		
		return ResponseEntity.ok(this.mvtService.findAll(page, size));
	}
	
	@DeleteMapping("/{stockmvt-id}")
	public ResponseEntity<StockMvtDtoResponse> deleteStockMvt (@PathVariable("stockmvt-id") @NotNull(message = "Product ID can not be null") String id){
		this.mvtService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
