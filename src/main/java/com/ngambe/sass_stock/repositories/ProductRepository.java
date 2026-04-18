package com.ngambe.sass_stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngambe.sass_stock.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	Optional<Product> findByReferenceIgnoreCase(final String reference);
}
