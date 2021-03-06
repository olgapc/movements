package com.movements.app.models.service;

import java.util.List;


import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;
import com.movements.app.models.entity.Information;
import com.movements.app.models.entity.Task;

public interface ITaskService {	
		
		//tasks methods
		public List<Task> findAll();

		public void save(Task task);
		
		public Task findTaskById(Long id);

		public void delete(Long id);
		
		//employees methods
		public Employee findEmployeeById(Long id);
		
		//companies methods
		public Company findCompanyById(Long id);
		
		//informations methods
		public List<Information> findInformationByDescription(String term);
		
		public Information findInformationById(Long id);
		
		public void deleteInformation(Long id);
		
		public List<Information> findAllInformations();
		
		public void saveInformation(Information information);
		
		public Task fetchByIdWithEmployeeWithCompanyWithTaskInformationWithInformationWithSubtask(Long id);
		
	
}
