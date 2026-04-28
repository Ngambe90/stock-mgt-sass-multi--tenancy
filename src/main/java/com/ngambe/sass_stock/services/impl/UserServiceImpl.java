package com.ngambe.sass_stock.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngambe.sass_stock.common.PageResponse;
import com.ngambe.sass_stock.config.TenantContext;
import com.ngambe.sass_stock.dto.request.UserRequest;
import com.ngambe.sass_stock.dto.response.UserResponse;
import com.ngambe.sass_stock.entities.User;
import com.ngambe.sass_stock.enumeration.UserRole;
import com.ngambe.sass_stock.exception.DuplicateRessourceException;
import com.ngambe.sass_stock.exception.InvalidRequestException;
import com.ngambe.sass_stock.mapper.UserMapper;
import com.ngambe.sass_stock.repositories.TenantRepository;
import com.ngambe.sass_stock.repositories.UserRepository;
import com.ngambe.sass_stock.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

	private final UserRepository repository;
	private final TenantRepository tenantRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return this.repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("no user was found with "+username));
	}
	@Override
	public void createUser(UserRequest request) {
		final String tenantId = TenantContext.getCurremtTenant();
		log.info("Creating user for Tenant: {}",tenantId);
		
		//validate if username exists
		if(this.repository.existsByUsername(request.getUsername())) {
			throw new DuplicateRessourceException("Username already exists");
		}
		// validate if email exists
		if(this.repository.existsByEmail(request.getEmail())) {
			throw new DuplicateRessourceException("Email already exists");
		}
		
		// validate role (ne peut pas etre PLATFORM_ADMIN)
		if (request.getRole() == UserRole.ROLE_PLATFORM_ADMIN) {
			throw new InvalidRequestException("Role is required");
		}
		User user = this.userMapper.toEntity(request);
		//user.setTenant(tenantId);
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		this.repository.save(user);
		
		log.info("User created successfully");
		
	}
	@Override
	public void updateUser(String userId, UserRequest request) {
		final String tenantId = TenantContext.getCurremtTenant();
		log.info("Updating user for Tenant: {}",tenantId);
		
		final User user = this.repository.findByIdAndNotDeleted(userId)
				.orElseThrow(()->new EntityNotFoundException("User dos not exists"));
		// check if user belongs to the tenant
		if(!user.getTenant().getId().equals(tenantId)) {
			throw new InvalidRequestException("User dos not belongs the tenant");
		}
		if(!user.getUsername().equals(request.getUsername()) && this.repository.existsByUsername(request.getUsername())){
			throw new InvalidRequestException("Username already exists");
		}
		if(!user.getEmail().equals(request.getEmail()) && this.repository.existsByEmail(request.getEmail())){
			throw new InvalidRequestException("Email already exists");
		}
		// validate role (ne peut pas etre PLATFORM_ADMIN)
		if (request.getRole() == UserRole.ROLE_PLATFORM_ADMIN) {
			throw new InvalidRequestException("Role is required");
		}
		// update user details
		
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setRole(request.getRole());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		this.repository.save(user);
		
		log.info("User updated successfully");
		
	}
	@Override
	public void deteleUser(String userId) {
		final String tenantId = TenantContext.getCurremtTenant();
		log.info("Updating user for Tenant: {}",tenantId);
		
		final User user = this.repository.findByIdAndNotDeleted(userId)
				.orElseThrow(()->new EntityNotFoundException("User dos not exists"));
		
		// check if user belongs to the tenant
		if(!user.getTenant().getId().equals(tenantId)) {
			throw new InvalidRequestException("User dos not belongs the tenant");
		}
		//soft delete user 
		user.setDeleted(true);
		this.repository.save(user);
		log.info("User deleted successfully");
	}
	@Override
	public UserResponse getUserById(String userId) {
		
		final User user = this.repository.findByIdAndNotDeleted(userId)
				.orElseThrow(()->new EntityNotFoundException("User dos not exists"));
		
		// check if user belongs to the tenant
				if(!user.getTenant().getId().equals(TenantContext.getCurremtTenant())) {
					throw new InvalidRequestException("User dos not belongs the tenant");
				}
		
		return this.userMapper.toResponse(user);
	}
	@Override
	public PageResponse<UserResponse> getAllUsers(int page, int size) {
		final String tenantId = TenantContext.getCurremtTenant();
		log.info("get all user for Tenant: {}",tenantId);
		final PageRequest pageRequest = PageRequest.of(page, size);
		final Page<User> pageUsers = this.repository.findAllByTenantId(tenantId,pageRequest);
		final Page<UserResponse> userResponses = pageUsers.map(this.userMapper::toResponse);
		
		return PageResponse.of(userResponses);
	}
	@Override
	public void enableUser(String userId) {
		final User user = this.repository.findByIdAndNotDeleted(userId)
				.orElseThrow(()->new EntityNotFoundException("User dos not exists"));
		
		// check if user belongs to the tenant
				if(!user.getTenant().getId().equals(TenantContext.getCurremtTenant())) {
					throw new InvalidRequestException("User dos not belongs the tenant");
				}
		user.setEnabled(true);
		this.repository.save(user);
		log.info("User Enabled successfully");
		
	}
	@Override
	public void desableUser(String userId) {
		final User user = this.repository.findByIdAndNotDeleted(userId)
				.orElseThrow(()->new EntityNotFoundException("User dos not exists"));
		
		// check if user belongs to the tenant
				if(!user.getTenant().getId().equals(TenantContext.getCurremtTenant())) {
					throw new InvalidRequestException("User dos not belongs the tenant");
				}
		user.setEnabled(false);
		this.repository.save(user);
		log.info("User disabled successfully");
	}

}
