package com.movements.app.models.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Long>{

	public List<Employee> findByNameLikeIgnoreCase(String term);
	
	@Query("select e from Employee e where e.company.id = ?1 and e.name like %?2%")
	public List<Employee> findByCompanyIdAndName(Long id, String term);
	
	
}
