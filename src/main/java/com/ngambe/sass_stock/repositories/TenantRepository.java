package com.ngambe.sass_stock.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngambe.sass_stock.entities.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String>{
	
	boolean existsByCompanyCode(String companyCode);
	
	boolean existsByEmail(String email);

}
