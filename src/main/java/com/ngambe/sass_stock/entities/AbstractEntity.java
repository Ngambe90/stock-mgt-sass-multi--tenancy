package com.ngambe.sass_stock.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ngambe.sass_stock.config.TenantContext;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @SuperBuilder

@FilterDef(
		name="tenantFilter",
		parameters = @ParamDef(name="tenantId",type = String.class),
		defaultCondition = "tenant_id = :tenantId"
		)
@Filter(name="tenantFilter")
public class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="Id", updatable = false, nullable = false)
	private String id;
	
	@Column(name = "tenant_id", nullable = false)
	private String tenantId;
	
	@CreatedDate
	@Column(name="created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name="updated_at", insertable = false)
	private LocalDateTime updatedAt;
	
	@CreatedBy
	@Column(name="created_by", updatable = false, nullable = false)
	private String createdBy;
	
	@LastModifiedBy
	@Column(name="updated_by", insertable = false)
	private String updatedBy;
	
	@Column(name="deleted", nullable = false)
	private Boolean deleted;
	
	@PrePersist
	protected void onCreate() {
		if(this.deleted == null) {
			this.deleted=Boolean.FALSE;
		}
		if(this.createdBy == null) {
			this.createdBy="SYSTEM";
		}
		if(this.tenantId == null) {
			this.tenantId=TenantContext.getCurremtTenant();
		}
	}
}
