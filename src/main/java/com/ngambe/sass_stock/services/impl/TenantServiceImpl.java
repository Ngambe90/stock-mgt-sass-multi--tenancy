package com.ngambe.sass_stock.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.dto.request.TenantRegisterRequest;
import com.ngambe.sass_stock.dto.response.TenantResponse;
import com.ngambe.sass_stock.entities.Tenant;
import com.ngambe.sass_stock.entities.User;
import com.ngambe.sass_stock.enumeration.TenantStatus;
import com.ngambe.sass_stock.enumeration.UserRole;
import com.ngambe.sass_stock.exception.DuplicateRessourceException;
import com.ngambe.sass_stock.exception.InvalidRequestException;
import com.ngambe.sass_stock.mapper.TenantMapper;
import com.ngambe.sass_stock.repositories.TenantRepository;
import com.ngambe.sass_stock.repositories.UserRepository;
import com.ngambe.sass_stock.services.ProvisioningService;
import com.ngambe.sass_stock.services.TenantService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor  @Slf4j
public class TenantServiceImpl  implements TenantService{
	
	private final TenantRepository tenantRepository;
	private final TenantMapper tenantMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ProvisioningService provisioningService; 
	
	@Override
	@Transactional
	public void TenantRegister(TenantRegisterRequest request) {
		// check if tenant exists by company code
		if(this.tenantRepository.existsByCompanyCode(request.getCompanyCode())) {
			throw new DuplicateRessourceException("Tenant already exist");
		}
		// check if email already exists
		if(this.tenantRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateRessourceException("Email already exist");
		}
		final Tenant tenant = this.tenantMapper.toEntity(request);
		tenant.setAdminPassword(this.passwordEncoder.encode(request.getAdminPassword()));
		tenant.setStatus(TenantStatus.PENDING);
		this.tenantRepository.save(tenant);
		
	}

	@Override
	public void approuveTenant(String tenantId) {
		// check if tenant exist
		final Tenant tenant = this.tenantRepository.findById(tenantId)
				.orElseThrow(()->new EntityNotFoundException("Tenant dos not exists"));
		
		//activate Tenant
		tenant.setStatus(TenantStatus.ACTIVE);
		this.tenantRepository.save(tenant);
		
		try {
			//provision the schema for the tenant
			this.provisioningService.provisionTenant(tenant);
			//create initial admin user
			createInitialAdminUser(tenant);
			
		} catch (final Exception e) {
			rollBacktenantStatus(tenant);
		}
		
		
	}

	

	@Override
	public void activedTenant(String tenantId) {
		final Tenant tenant = this.tenantRepository.findById(tenantId)
				.orElseThrow(()->new EntityNotFoundException("Tenant dos not exists"));
		
		if(tenant.getStatus()!=TenantStatus.PENDING) {
			throw new InvalidRequestException("Tenant is not pending");
		}
		tenant.setStatus(TenantStatus.ACTIVE);
		this.tenantRepository.save(tenant);
	}

	@Override
	public void deactivedTenant(String tenantId) {
		final Tenant tenant = this.tenantRepository.findById(tenantId)
				.orElseThrow(()->new EntityNotFoundException("Tenant dos not exists"));
		
		if(tenant.getStatus()!=TenantStatus.ACTIVE) {
			throw new InvalidRequestException("Tenant is not active");
		}
		tenant.setStatus(TenantStatus.INACTIVE);
		this.tenantRepository.save(tenant);
	}

	@Override
	public void suspendTenant(String tenantId) {
		final Tenant tenant = this.tenantRepository.findById(tenantId)
				.orElseThrow(()->new EntityNotFoundException("Tenant dos not exists"));
		
		if(tenant.getStatus()!=TenantStatus.ACTIVE) {
			throw new InvalidRequestException("Tenant is not active");
		}
		tenant.setStatus(TenantStatus.SUSPENDED);
		this.tenantRepository.save(tenant);
		
	}

	@Override
	public PageResponse<TenantResponse> findAll(int page, int size) {
		final PageRequest pageRequest = PageRequest.of(page,size);
		final Page<Tenant> tenants = this.tenantRepository.findAll(pageRequest);
		final Page<TenantResponse> tenantResponse = tenants.map(this.tenantMapper::toResponse);
		return PageResponse.of(tenantResponse);
	}
	
	private void createInitialAdminUser(Tenant tenant) {
			
			//check if the user already exists
			if(this.userRepository.existsByUsername(tenant.getAdminUsername())) {
				throw new DuplicateRessourceException("User already exist");
			}
			// create the user Admin for the company
			final User adminUser = User.builder()
					.username(tenant.getAdminUsername())
					.email(tenant.getAdminEmail())
					.firstName(extractFirstName(tenant.getAdminFullName()))
					.lastName(extractLastName(tenant.getAdminFullName()))
					.password(this.passwordEncoder.encode(tenant.getAdminPassword()))
					.role(UserRole.ROLE_COMPANY_ADMIN)
					.tenant(tenant)
					.enabled(true)
					.build();
			this.userRepository.save(adminUser);
			
			log.info("Created initial admin user for tenant {}",tenant.getId());
		}
	private String extractFirstName(final String fullName) {
		return fullName.split("")[0];
	}
	
	private String extractLastName(final String fullName) {
		return fullName.split("").length >1 ? fullName.split(" ")[1]:fullName;
	}
	
	private void rollBacktenantStatus(Tenant tenant) {
		tenant.setStatus(TenantStatus.PENDING);
		this.tenantRepository.save(tenant);
	}



}
