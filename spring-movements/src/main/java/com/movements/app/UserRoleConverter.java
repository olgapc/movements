package com.movements.app;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movements.app.models.dao.IRoleDao;
import com.movements.app.models.dao.IUserDao;
import com.movements.app.models.entity.Role;
import com.movements.app.models.pks.UserRolePK;

//@Converter
public class UserRoleConverter implements AttributeConverter<List<Role>, String[]> {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUserDao userDao;

	@Override
	public String[] convertToDatabaseColumn(List<Role> roles) {
		String[] stringRoles = new String[roles.size()];
		int i = 0;
		for (Role role : roles) {
			stringRoles[i] = role.getId().toString();
			i++;
		}
		return stringRoles;
	}

	@Override
	public List<Role> convertToEntityAttribute(String[] joined) {
		if (joined == null)
			return Arrays.asList();
		List<Role> roles = new ArrayList<Role>();
		for (int i = 1; i < joined.length; i++) {

			roles.add(roleDao.findById(Long.parseLong(joined[i])).orElse(null));
		}
		return roles;
	}

}

/*
 * public class UserRoleConverter implements AttributeConverter<UserRole,
 * UserRolePK> {
 * 
 * @Autowired private IRoleDao roleDao;
 * 
 * @Autowired private IUserDao userDao;
 * 
 * @Override public UserRolePK convertToDatabaseColumn(UserRole attribute) { if
 * (attribute == null) { return null; } UserRolePK columns = new UserRolePK();
 * columns.setRoleId(attribute.getRole().getId());
 * columns.setUserId(attribute.getUser().getId()); return columns; }
 * 
 * @Override public UserRole convertToEntityAttribute(UserRolePK dbData) { if
 * (dbData == null) { // check if empty? return null; } return new
 * UserRole(userDao.findById(dbData.getUserId()).orElse(null),
 * roleDao.findById(dbData.getRoleId()).orElse(null)); }
 * 
 * }
 */