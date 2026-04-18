package com.ngambe.sass_stock.entities;

import java.time.LocalDateTime;

import com.ngambe.sass_stock.entities.enumeration.TypeMvt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="Stock_mvts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class StockMvt extends AbstractEntity{

	@Column(name="type_mvt", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeMvt typeMvt;
	@Column(name="quantity", nullable = false)
	private Integer qunatity;
	@Column(name="date_mvt", nullable = false)
	private LocalDateTime dateMvt;
	@Column(name="Comment", columnDefinition = "TEXT")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
}
