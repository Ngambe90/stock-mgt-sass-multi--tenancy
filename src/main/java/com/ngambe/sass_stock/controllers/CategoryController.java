package com.ngambe.sass_stock.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.CategorieDtoRequest;
import com.ngambe.sass_stock.dto.response.CategorieDtoResponse;
import com.ngambe.sass_stock.services.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor @RequestMapping("api/v1/categories")
@Tag(name="Category", description = "Category API")
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<Void> createCategory(@RequestBody @ Valid final CategorieDtoRequest request){
		this.categoryService.create(request);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{categorie-id}")
	public ResponseEntity<Void> updateCategory(@RequestBody @ Valid final CategorieDtoRequest request, @PathVariable("categorie-id") String id){
		this.categoryService.update(id, request);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/{categorie-id}")
	public ResponseEntity<CategorieDtoResponse> getCategoryId (@PathVariable("categorie-id") String id){
		this.categoryService.findById(id);
		return ResponseEntity.accepted().build();
	}
	@GetMapping
	public ResponseEntity<PageResponse<CategorieDtoResponse>> getAllCategoryId (
			@RequestParam( defaultValue = "0")
			final int page, 
			@RequestParam( defaultValue = "10")
			final int size){
		
		return ResponseEntity.ok(this.categoryService.findAll(page, size));
	}
	
	@DeleteMapping("/{categorie-id}")
	public ResponseEntity<CategorieDtoResponse> deleteCategory (@PathVariable("categorie-id") String id){
		this.categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
