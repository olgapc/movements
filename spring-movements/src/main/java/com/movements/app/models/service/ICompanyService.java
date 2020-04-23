package com.movements.app.models.service;

import java.util.List;


import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.CompanyType;

public interface ICompanyService {
	
	public List<Company> findAll();

	public void save(Company company);

	public Company findById(Long id);

	public Company fetchByIdWithTasksWithEmployees(Long id);
	
	public void delete(Long id);
	
	public List<Company> findCompanyByName(String term);

	public List<CompanyType> findAllCompanyType();
	
	public CompanyType findCompanyTypeById(Long id);

}
