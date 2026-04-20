package com.ngambe.sass_stock.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Aspect
@Component
public class TenantHibernateFilter {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Before("execution(* com.ngambe.sass_stock.services.*.*(..))")
	public void activateTenantFilter() {
		final String tenantId=TenantContext.getCurremtTenant();
		
		if(tenantId !=null) {
			final Session session = this.entityManager.unwrap(Session.class);
			session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
		}
	}
}
