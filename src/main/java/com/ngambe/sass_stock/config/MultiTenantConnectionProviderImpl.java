package com.ngambe.sass_stock.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String>,HibernatePropertiesCustomizer {
	
	private final DataSource dataSource;
	
	@Override
	public boolean isUnwrappableAs(Class<?> unwrapType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
		
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		
		return this.dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		log.debug("Getting connection for tenant: {}",tenantIdentifier);
		final Connection connection = getAnyConnection();
		try {
			if(tenantIdentifier !=null && !tenantIdentifier.equals("public")) {
				connection.createStatement().execute("SET search_path TO "+ tenantIdentifier +", public");
				log.debug("set search_path to: {}",tenantIdentifier);
			}
		} catch (final SQLException e) {
			log.error("Error Getting connection for tenant: {}",tenantIdentifier,e);
			throw e;
		}
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("SET search_path TO public");
			
		} catch (final SQLException e) {
			log.error("Error Getting connection for tenant: {}",tenantIdentifier,e);
			
		}
		connection.close();
		
	}

	@Override
	public boolean supportsAggressiveRelease() {
		
		return false;
	}
	
	

}
