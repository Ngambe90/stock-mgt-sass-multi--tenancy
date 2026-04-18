package com.ngambe.sass_stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngambe.sass_stock.entities.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, String> {
 
	Optional<Categorie>  findByCategorieNameIgnoreCase(String categorieName);
	
	
}
