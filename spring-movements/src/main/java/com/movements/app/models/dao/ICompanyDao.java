package com.movements.app.models.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Company;
import com.movements.app.models.entity.Employee;


public interface ICompanyDao extends CrudRepository<Company, Long>{

	//@Query("select c from Company c where c.name like %?1%")
	//public List<Company> findByName(String term);
	
	public List<Company> findByNameLikeIgnoreCase(String term);

	public Optional <Company> findByName(String name);
	
	//@Query("from Company c inner join fetch c.employees where c.id = ?1")
	//public List<Employee> findByCompanyIdAndNameLikeIgnoreCase(Long id, String term);
	
	
}
