package com.ngambe.sass_stock.entities;



import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ngambe.sass_stock.enumeration.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@Entity
@Table(name = "users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class User extends AbstractEntity implements UserDetails{
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tenant_id", foreignKey = @ForeignKey(name="fk_tenant_id"))
	private Tenant tenant;
	
	@Column(name = "username",nullable = false, unique = true)
	private String username;
	
	@Column(name = "email",nullable = false, unique = true)
	private String email;
	
	@Column(name = "password",nullable = false)
	private String password;
	
	@Column(name = "first_name",nullable = false)
	private String firstName;
	@Column(name = "last_name",nullable = false)
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role",nullable = false)
	private UserRole role;
	
	@Column(name = "enabled")
	private boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(this.role.name()));
	}
	
	public String getTenantId() {
		if (this.tenant!=null) {
			return this.tenant.getId();
		}
		return null;
	}

	
}
