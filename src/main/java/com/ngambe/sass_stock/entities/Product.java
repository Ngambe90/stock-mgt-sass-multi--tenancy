package com.ngambe.sass_stock.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class Product extends AbstractEntity{
	
	@Column(name="name", nullable = false)
	private String product_name;
	@Column(name="Reference", nullable = false, unique = true)
	private String reference;
	@Column(name="Description", nullable = false,columnDefinition = "TEXT" )
	private String description;
	@Column(name="Price", nullable = false)
	private BigDecimal price;
	//private Integer quantit;
	@Column(name="alerteThresholds", nullable = false)
	private Integer alerteThresholds;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categorie categorie;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<StockMvt> stockMouvements;
}
