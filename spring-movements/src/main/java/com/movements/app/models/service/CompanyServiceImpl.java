package com.movements.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.ICompanyDao;
import com.movements.app.models.entity.Company;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyDao companyDao;

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
	@Transactional
	public void delete(Long id) {
		companyDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Company> findCompanyByName(String term) {
		return companyDao.findByNameLikeIgnoreCase("%"+term+"%");
	}

}
