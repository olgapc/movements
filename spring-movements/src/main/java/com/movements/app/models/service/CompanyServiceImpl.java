package com.movements.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.ICompanyDao;
import com.movements.app.models.dao.ICompanyTypeDao;
import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.CompanyType;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private ICompanyTypeDao companyTypeDao;

	@Override
	@Transactional(readOnly = true)
	public List<Company> findAll() {
		return (List<Company>) companyDao.findAll();
	}

	@Override
	@Transactional
	public void save(Company company) {
		companyDao.save(company);
	}

	@Override
	@Transactional(readOnly = true)
	public Company findOne(Long id) {
		return companyDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Company fetchByIdWithTasksWithEmployees(Long id) {
		return companyDao.fetchByIdWithTasksWithEmployees(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		companyDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Company> findCompanyByName(String term) {
		return companyDao.findByNameLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional(readOnly = true)
	public List<CompanyType> findAllCompanyType() {
		return (List<CompanyType>) companyTypeDao.findAll();
	}

	@Override
	public CompanyType findByIdCompanyType(Long id) {
		return companyTypeDao.findById(id).orElse(null);
	}

	
	
}
