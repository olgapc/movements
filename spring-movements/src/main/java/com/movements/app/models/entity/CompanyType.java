package com.movements.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company_types")
public class CompanyType implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String description;

	public CompanyType() {

	}

	
	public CompanyType(Long id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}



	public CompanyType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String description) {
		this.description = description;
	}

	private static final long serialVersionUID = 1L;
}
