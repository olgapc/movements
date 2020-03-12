package com.movements.app.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.ICompanyDao;
import com.movements.app.models.dao.IEmployeeDao;
import com.movements.app.models.dao.IInformationDao;
import com.movements.app.models.dao.ITaskDao;
import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;

@Service
public class TaskServiceImpl implements ITaskService{

	
	@Autowired
	private ITaskDao taskDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IEmployeeDao employeeDao;
	
	@Autowired
	private IInformationDao informationDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {		
		return (List<Task>) taskDao.findAll();
	}

	@Override
	@Transactional
	public void save(Task task) {
		taskDao.save(task);	
	}


	@Override
	@Transactional
	public void delete(Long id) {
		taskDao.deleteById(id);	
	}

	@Override
	@Transactional (readOnly=true)
	public Employee findEmployeeById(Long id) {
		return employeeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Company findCompanyById(Long id) {
		return companyDao.findById(id).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public List<Information> findByDescription(String term) {
		return informationDao.findByDescriptionLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional (readOnly=true)
	public Information findInformationById(Long id) {
		return informationDao.findById(id).orElse(null);
	}

	@Override
	@Transactional (readOnly=true)
	public Task findTaskById(Long id) {
		return taskDao.findById(id).orElse(null);
	}



	
	
}
