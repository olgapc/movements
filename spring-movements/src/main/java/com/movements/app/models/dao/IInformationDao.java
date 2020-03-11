package com.movements.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Information;

public interface IInformationDao extends CrudRepository<Information, Long>{

	@Query("select i from Information i where i.description like %?1%")
	public List<Information> findByDescription(String term);
	
	public List<Information> findByDescriptionLikeIgnoreCase(String term);
	
	
}
