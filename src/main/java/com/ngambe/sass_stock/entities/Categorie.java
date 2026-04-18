package com.ngambe.sass_stock.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="Categories")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class Categorie extends AbstractEntity{

	@Column(name="CategorieName", nullable = false, unique = true)
	private String categorieName;
	
	@Column(name="Description", nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@OneToMany(mappedBy = "categorie",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products;
}
