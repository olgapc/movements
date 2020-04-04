package com.movements.app.models.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.ICompanyDao;
import com.movements.app.models.dao.IEmployeeDao;
import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;
	
	@Autowired
	private ICompanyDao companyDao;

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return (List<Employee>) employeeDao.findAll();
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		employeeDao.save(employee);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee findOne(Long id) {
		return employeeDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Employee fetchByIdWithTasksWithCompany(Long id) {
		return employeeDao.fetchByIdWithTasksWithCompany(id);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		employeeDao.deleteById(id);

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findEmployeeByName(String term) {	
		return employeeDao.findByNameLikeIgnoreCase("%"+term+"%");
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findEmployeeByCompanyIdAndName(Long id, String term) {
		return employeeDao.findByCompanyIdAndName(id, term);
	}

	@Override
	@Transactional(readOnly = true)
	public Company findCompanyById(Long id) {
		return companyDao.findById(id).orElse(null);
	}






	
	
}
