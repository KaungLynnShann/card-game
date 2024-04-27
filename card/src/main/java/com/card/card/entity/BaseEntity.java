package com.card.card.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@MappedSuperclass
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class BaseEntity {
	
	@Column(name = "CREATED_TIME", nullable= false)
	protected OffsetDateTime createdAt;
	@Column(name = "UPDATED_TIME", nullable= false)
	protected OffsetDateTime updatedAt;
	
	@PrePersist
	public void preInsert() {
		this.createdAt = OffsetDateTime.now(); 
		this.updatedAt = this.createdAt; 
	}
	
	@PreUpdate
	public void preUpdate() {		
		this.updatedAt = OffsetDateTime.now(); 
		this.createdAt = OffsetDateTime.now(); 
	}
	
}
