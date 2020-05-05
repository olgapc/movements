package com.movements.app.models.service;

import java.util.List;

import com.movements.app.models.entity.AppUser;
import com.movements.app.models.entity.Role;

public interface IUserService {
	
	public List<AppUser> findAll();

	public void save(AppUser user);

	public AppUser findById(String id);
	
	public void delete(String id);

	public List<Role> listRoles();
	
	public Role findRoleById(Long id);
	
}
