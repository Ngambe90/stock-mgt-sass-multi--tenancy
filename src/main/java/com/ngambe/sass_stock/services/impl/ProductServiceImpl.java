package com.ngambe.sass_stock.services.impl;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.ProductDtoRequest;
import com.ngambe.sass_stock.dto.response.ProductDtoResponse;
import com.ngambe.sass_stock.entities.Categorie;
import com.ngambe.sass_stock.entities.Product;
import com.ngambe.sass_stock.mapper.ProductMapper;
import com.ngambe.sass_stock.repositories.CategorieRepository;
import com.ngambe.sass_stock.repositories.ProductRepository;
import com.ngambe.sass_stock.services.ProductService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;
	private final CategorieRepository categorieRepository;
	private final ProductMapper productMapper;
	@Override
	public void create(ProductDtoRequest request) {
		checkIfProductAlreadyExistByReference(request.getReference());
		checkIfCategoryExistById(request.getCategoryId());
		final Product product = this.productMapper.toEntity(request);
		this.productRepository.save(product);
		
	}

	@Override
	public void update(String id, ProductDtoRequest request) {
		Optional<Product> productExist = this.productRepository.findById(id);
		if(productExist.isEmpty()) {
			log.debug("product dos not exist");
			throw new RuntimeException("product dos not exist");
		}
		checkIfProductAlreadyExistByReference(request.getReference());
		checkIfCategoryExistById(request.getCategoryId());
		final Product productUpdate = this.productMapper.toEntity(request);
		productUpdate.setId(id);
		this.productRepository.save(productUpdate);
	}

	@Override
	public PageResponse<ProductDtoResponse> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Product> products = this.productRepository.findAll(pageRequest);
		Page<ProductDtoResponse> productResponses = products.map(this.productMapper::toResponse);
		return PageResponse.of(productResponses);
	}

	@Override
	public ProductDtoResponse findById(String id) {
		
		return this.productRepository.findById(id).map(this.productMapper::toResponse)
				.orElseThrow(()->new EntityNotFoundException("Product dos not Exist"));
	}

	@Override
	public void delete(String id) {
		Product product= this.productRepository.findById(id)
		.orElseThrow(()->new EntityNotFoundException("Product dos not Exist"));
		
		this.productRepository.delete(product);
		
	}
	
	private void checkIfProductAlreadyExistByReference(String reference) {
		Optional<Product> product = this.productRepository.findByReferenceIgnoreCase(reference);
		if(product.isPresent()) {
			log.debug("product already exists");
			throw new RuntimeException("product already exists");
		}
	}
	private void checkIfCategoryExistById(final String id) {
		Optional<Categorie> category = this.categorieRepository.findById(id);
		if(category.isEmpty()) {
			throw new RuntimeException("Category dos not exists");
		}
	}

}
