package com.movements.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movements.app.models.dao.IRoleDao;
import com.movements.app.models.dao.IUserDao;
import com.movements.app.models.entity.AppUser;
import com.movements.app.models.entity.Role;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	

	@Override
	@Transactional(readOnly = true)
	public List<AppUser> findAll() {
		return (List<AppUser>) userDao.findAll();
	}

	@Override
	@Transactional
	public void save(AppUser user) {
		userDao.save(user);	
	}

	@Override
	@Transactional(readOnly = true)
	public AppUser findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		userDao.deleteById(id);		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> listRoles() {
		return (List<Role>) roleDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Role findRoleById(Long id) {
		return roleDao.findById(id).orElse(null);
	}

	
	
}
