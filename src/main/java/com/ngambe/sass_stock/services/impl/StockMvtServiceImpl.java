package com.ngambe.sass_stock.services.impl;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.StockMvtDtoRequest;
import com.ngambe.sass_stock.dto.response.StockMvtDtoResponse;
import com.ngambe.sass_stock.entities.Product;
import com.ngambe.sass_stock.entities.StockMvt;
import com.ngambe.sass_stock.mapper.StockMvtMapper;
import com.ngambe.sass_stock.repositories.ProductRepository;
import com.ngambe.sass_stock.repositories.StockMvtRepository;
import com.ngambe.sass_stock.services.StockMvtService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class StockMvtServiceImpl implements StockMvtService{
	
	private final StockMvtRepository mvtRepository;
	private final ProductRepository productRepository;
	private final StockMvtMapper mvtMapper;

	@Override
	public void create(StockMvtDtoRequest request) {
		checkIfProductExistById(request.getProductId());
		StockMvt stockMvt = this.mvtMapper.toEntity(request);
		this.mvtRepository.save(stockMvt);
		
	}

	@Override
	public void update(String id, StockMvtDtoRequest request) {
		final Optional<StockMvt> stockMvt = this.mvtRepository.findById(id);
		if(stockMvt.isEmpty()) {
			throw new EntityNotFoundException("Mouvements dos not Exist");
		}
		checkIfProductExistById(request.getProductId());
		final StockMvt stockMvtUpdate = this.mvtMapper.toEntity(request);
		stockMvtUpdate.setId(id);
		this.mvtRepository.save(stockMvtUpdate);
	}

	@Override
	public PageResponse<StockMvtDtoResponse> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<StockMvt> stockMvts = this.mvtRepository.findAll(pageRequest);
		Page<StockMvtDtoResponse> stockMvtResponse = stockMvts.map(this.mvtMapper::toResponse);
		return PageResponse.of(stockMvtResponse);
	}

	@Override
	public StockMvtDtoResponse findById(String id) {
		
		return this.mvtRepository.findById(id).map(this.mvtMapper::toResponse)
				.orElseThrow(()->new RuntimeException("Mouvements dos not Exist"));
	}

	@Override
	public void delete(String id) {
		StockMvt stockMvts=	this.mvtRepository.findById(id)
		.orElseThrow(()->new RuntimeException("Mouvements dos not Exist"));
		this.mvtRepository.delete(stockMvts);
		
	}
	
	private void checkIfProductExistById(String id) {
		Optional<Product> product = this.productRepository.findById(id);
		if(product.isEmpty()) {
			log.debug("product dos not exists");
			throw new EntityNotFoundException("product dos not exists");
		}
	}

}
