package com.movements.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movements.app.models.service.IUserService;

@Component
public class AuthoritiesEditor extends PropertyEditorSupport{

	@Autowired
	private IUserService service;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Long id = Long.parseLong(text);
			setValue(service.findAuthorityById(id));
		} catch (NumberFormatException e){
			setValue(null);
		}
		super.setAsText(text);
	}

	
	
}
