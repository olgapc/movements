package com.movements.app.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;

public interface ITaskService {	
		
		public List<Task> findAll();

		public void save(Task task);

		public void delete(Long id);
		
		public Employee findEmployeeById(Long id);
		
		public Company findCompanyById(Long id);
		
		public List<Information> findByDescription(String term);
		
		public Information findInformationById(Long id);
		
		public Task findTaskById(Long id);
		
		public void deleteInformation(Long id);
		
		public List<Information> findAllInformations();
		
		public void saveInformation(Information information);
		
	
}
