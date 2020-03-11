package com.movements.app.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;

public interface IEmployeeService {
	
	public List<Employee> findAll();

	public void save(Employee employee);

	public Employee findOne(Long id);

	public void delete(Long id);
	
	public List<Employee> findEmployeeByName(String term);
	
	@EntityGraph(value = "Employee.company", type = EntityGraphType.FETCH)
	public List<Employee> findEmployeeByCompanyIdAndName(Long id, String term);
	
	public Company findCompanyById(Long id);
}
