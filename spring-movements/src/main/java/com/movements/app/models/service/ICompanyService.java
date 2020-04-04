package com.movements.app.models.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.entity.Company;

public interface ICompanyService {
	
	public List<Company> findAll();

	public void save(Company company);

	public Company findOne(Long id);

	public Company fetchByIdWithTasksWithEmployees(Long id);
	
	public void delete(Long id);
	
	public List<Company> findCompanyByName(String term);


}
