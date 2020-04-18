package com.movements.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

		Employee employee = (Employee) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.employee.name");

		if (errors.getFieldValue("name") != null) {
			String value = errors.getFieldValue("name").toString();
			if (value.length() > 0) {
				char[] chars = value.toCharArray();

				for (char c : chars) {
					if (Character.isDigit(c)) {
						errors.rejectValue("name", "Pattern.employee.name");
						break;
					}
				}
			}
		}

		if (!employee.getNif().matches("[X-Y]?[0-9]{8}[A-Z]?")) {
			errors.rejectValue("nif", "Pattern.employee.nif");
		}

		if (!employee.getNaf().matches("[\\d]{1,2}[-][\\d]{7,8}[-][\\d]{2}")) {
			errors.rejectValue("naf", "Pattern.employee.naf");
		}

	}

}
