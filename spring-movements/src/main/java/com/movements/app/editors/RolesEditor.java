package com.movements.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movements.app.models.service.IUserService;

@Component
public class RolesEditor extends PropertyEditorSupport{

	@Autowired
	private IUserService service;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Long id = Long.parseLong(text);
			setValue(service.findRoleById(id));
		} catch (NumberFormatException e){
			setValue(null);
		}
		super.setAsText(text);
	}

	
	
}
