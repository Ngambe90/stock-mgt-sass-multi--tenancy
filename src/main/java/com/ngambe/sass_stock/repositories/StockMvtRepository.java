package com.ngambe.sass_stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngambe.sass_stock.entities.StockMvt;

@Repository
public interface StockMvtRepository extends JpaRepository<StockMvt, String>{

}
