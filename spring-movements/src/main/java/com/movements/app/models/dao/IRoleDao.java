package com.movements.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Role;


public interface IRoleDao extends CrudRepository<Role, Long>{

	public List<Role> findByRoleLikeIgnoreCase(String term);
}
