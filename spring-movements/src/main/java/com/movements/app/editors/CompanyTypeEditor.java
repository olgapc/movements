package com.movements.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movements.app.models.service.ICompanyService;

@Component
public class CompanyTypeEditor extends PropertyEditorSupport {

	@Autowired
	private ICompanyService companyService;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		if (idString != null && idString.length() > 0) {
			try {
				Long id = Long.parseLong(idString);
				this.setValue(companyService.findCompanyTypeById(id));
			} catch (NumberFormatException e) {
				setValue(null);
			}
		} else {
			setValue(null);
		}
	}

}
