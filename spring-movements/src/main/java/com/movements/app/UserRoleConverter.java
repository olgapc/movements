package com.movements.app;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.movements.app.models.dao.IRoleDao;
import com.movements.app.models.dao.IUserDao;
import com.movements.app.models.entity.UserRole;
import com.movements.app.models.pks.UserRolePK;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, UserRolePK> {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUserDao userDao;

	@Override
	public UserRolePK convertToDatabaseColumn(UserRole attribute) {
		if (attribute == null) {
			return null;
		}
		UserRolePK columns = new UserRolePK();
		columns.setRoleId(attribute.getRole().getId());
		columns.setUserId(attribute.getUser().getId());
		return columns;
	}

	@Override
	public UserRole convertToEntityAttribute(UserRolePK dbData) {
		if (dbData == null) { // check if empty?
			return null;
		}
		return new UserRole(userDao.findById(dbData.getUserId()).orElse(null),
				roleDao.findById(dbData.getRoleId()).orElse(null));
	}

}