package com.movements.app.models.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Company;



public interface ICompanyDao extends CrudRepository<Company, Long>{

	//@Query("select c from Company c where c.name like %?1%")
	//public List<Company> findByName(String term);
	
	public List<Company> findByNameLikeIgnoreCase(String term);

	public Optional <Company> findByName(String name);	
	
	@Query(nativeQuery = true, value="select * from companies c "
			+ "left join tasks t on c.id = t.id "
			+ "left join employees e on c.id = e.id "
			+ "where c.id = ?#{[0]}")
	public Company fetchByIdWithTasksWithEmployees(Long id);
	
	
}
