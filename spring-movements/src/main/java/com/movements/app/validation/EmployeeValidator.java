package com.movements.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.movements.app.models.entity.Employee;

@Component
public class EmployeeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Employee employee = (Employee)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.employee.name");
		
		if(!employee.getNif().matches("[X-Y]?[0-9]{8}[A-Z]?")) {
			errors.rejectValue("nif", "Pattern.employee.nif");
		}
		
		if(!employee.getNaf().matches("[\\d]{1,2}[-][\\d]{7,8}[-][\\d]{2}")) {
			errors.rejectValue("nif", "Pattern.employee.naf");
		}
		

	}

}
