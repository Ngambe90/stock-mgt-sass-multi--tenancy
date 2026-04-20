package com.ngambe.sass_stock.services.impl;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.CategorieDtoRequest;
import com.ngambe.sass_stock.dto.response.CategorieDtoResponse;
import com.ngambe.sass_stock.entities.Categorie;
import com.ngambe.sass_stock.exception.DuplicateRessourceException;
import com.ngambe.sass_stock.mapper.CategoryMapper;
import com.ngambe.sass_stock.repositories.CategorieRepository;
import com.ngambe.sass_stock.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor @Slf4j @Transactional
public class CategoryServiceImp implements CategoryService{

	private final CategorieRepository categorieRepository;
	private final CategoryMapper categoryMapper;
	@Override
	public void create(CategorieDtoRequest request) {
		checkIfCategoryAlreadyExistByName(request.getCategorieName());
		final Categorie entity = this.categoryMapper.toEntity(request);
		this.categorieRepository.save(entity);
		
	}

	@Override
	public void update(String id, CategorieDtoRequest request) {
		Optional<Categorie> category = categorieRepository.findById(id);
		if(category.isEmpty()) {
			log.debug("Category dos not exists");
			throw new RuntimeException("Category dos not exists");
		}
		checkIfCategoryAlreadyExistByName(request.getCategorieName());
		final Categorie categoryToUpdate = this.categoryMapper.toEntity(request);
		categoryToUpdate.setId(id);
		this.categorieRepository.save(categoryToUpdate);
		
	}

	@Override
	public PageResponse<CategorieDtoResponse> findAll(int page, int size) {
		final PageRequest pageRequest = PageRequest.of(page, size);
		final Page<Categorie> categories = this.categorieRepository.findAll(pageRequest);
		final Page<CategorieDtoResponse> categoryResponse = categories.map(this.categoryMapper::toResponse);
		return PageResponse.of(categoryResponse);
	}

	@Override
	public CategorieDtoResponse findById(String id) {
		
		return this.categorieRepository.findById(id).map(this.categoryMapper::toResponse)
				.orElseThrow(()->new EntityNotFoundException("Category dos not Exist"));
	}

	@Override
	public void delete(String id) {
		Categorie category= this.categorieRepository.findById(id)
		.orElseThrow(()->new EntityNotFoundException("Category dos not Exist"));
		this.categorieRepository.delete(category);
		
	}
	
	private void checkIfCategoryAlreadyExistByName(String categoryName) {
		Optional<Categorie> category = categorieRepository.findByCategorieNameIgnoreCase(categoryName);
		if(category.isPresent()) {
			log.debug("Category already exists");
			throw new DuplicateRessourceException("Category already exists");
		}
	}

}
