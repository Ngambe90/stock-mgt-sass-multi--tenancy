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
import com.ngambe.sass_stock.dto.request.ProductDtoRequest;
import com.ngambe.sass_stock.dto.response.CategorieDtoResponse;
import com.ngambe.sass_stock.dto.response.ProductDtoResponse;
import com.ngambe.sass_stock.services.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor @RequestMapping("api/v1/products")
@Tag(name="Product", description = "Product API")
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody @ Valid final ProductDtoRequest request){
		this.productService.create(request);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{product-id}")
	public ResponseEntity<Void> updateProduct(@RequestBody @ Valid final 
			ProductDtoRequest request, 
			@PathVariable("product-id")
			@NotNull(message = "Product ID can not be null")
			String id){
		this.productService.update(id, request);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/{product-id}")
	public ResponseEntity<CategorieDtoResponse> getProductById (@PathVariable("product-id") @NotNull(message = "Product ID can not be null")String id){
		this.productService.findById(id);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	public ResponseEntity<PageResponse<ProductDtoResponse>> getAllProducts (
			@RequestParam( defaultValue = "0")
			final int page, 
			@RequestParam( defaultValue = "10")
			final int size){
		
		return ResponseEntity.ok(this.productService.findAll(page, size));
	}
	
	@DeleteMapping("/{product-id}")
	public ResponseEntity<CategorieDtoResponse> deleteProduct (@PathVariable("product-id") @NotNull(message = "Product ID can not be null") String id){
		this.productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
