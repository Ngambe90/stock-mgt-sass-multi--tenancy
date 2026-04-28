package com.ngambe.sass_stock.services.impl;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.entities.Tenant;
import com.ngambe.sass_stock.exception.TenantProvisioningException;
import com.ngambe.sass_stock.services.ProvisioningService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ProvisioningServiceImpl implements ProvisioningService{
	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;

	@Override
	public void provisionTenant(Tenant tenant) {
		final String schemaName = "tenant_" +tenant.getCompanyCode().toLowerCase();
		
		try {
			log.info("Provisioning tenant {} (schema : {})", tenant.getCompanyName(), schemaName);
			//1. create postgres schema
			createSchema(schemaName);
			log.info("Schema created successfully: {}", schemaName);
			//2. run flyway migration for this schemas
			
			runTenantMigrations(schemaName);
			log.info("Tenant Migrations completed successfully for schema: {}", schemaName);
			// 3. initialize the default data(Optionnel)
			initializeDefaultData(schemaName, tenant);
		} catch (Exception e) {
			log.error("Failed to provision Tenant: {}",tenant.getCompanyCode(),e);
			
			
			try {
				//rollback: drop schema creation
				dropSchema(schemaName);
			} catch (Exception e2) {
				log.error("Failed to rollback schema for Tenant: {}",tenant.getCompanyCode(),e2);
			}
			throw new TenantProvisioningException("Failed to provision Tenant "+e);
		}
	}

	

	private void dropSchema(String schemaName) {
		final String sql = String.format("DROP SCHEMA IF EXISTS %s CASCADE", schemaName);
		this.jdbcTemplate.execute(sql);
		
	}



	private void runTenantMigrations(String schemaName) {
		log.info("Running tenant Migration for schema{} ",  schemaName);
		final Flyway tenantFlyway = Flyway.configure()
				.dataSource(this.dataSource)
				.schemas(schemaName)
				.locations("classpath:db/migration/tenant")
				.baselineOnMigrate(true)
				.table("flyway_schema_history")
				.validateOnMigrate(true)
				.cleanDisabled(true)
				.load();
		
		
		log.info(" tenant Migration started");
		tenantFlyway.migrate();
		log.info(" tenant Migration completed");
		
	}

	private void createSchema(String schemaName) {
		final String sql = String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName);
		this.jdbcTemplate.execute(sql);
		
	}
	
	private void initializeDefaultData(String schemaName, Tenant tenant) {
		
		
	}

}
