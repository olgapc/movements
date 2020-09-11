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

	public String checkDNI(String dni) {

		boolean isPassport = false;
		StringBuilder newDNI = new StringBuilder();
		String numDNI = null;
		String firstCapitalLetter = null;
		String calculatedLastCapitalLetter = null;
		int beginIndex = 0;
		int endIndex = dni.length() - 1;
		int numNIE = 0;

		dni = dni.toUpperCase();
		

		// if length up to 9 is a Passport
		if (dni.length() > 9) {

			isPassport = true;
			newDNI.append("passport");


		} else {

			// if first character is a letter, check if it's a NIE
			if (Character.isLetter(dni.charAt(beginIndex))) {

				// turn first letter in number
				firstCapitalLetter = dni.substring(0, 1);

				numNIE = turnLetterToNum(firstCapitalLetter);

				// if the method returns '9', the letter doesn't exist for a NIE, and it's a
				// Passport
				if (numNIE == 9) {
					isPassport = true;
					newDNI.append("passport");

				} else {

					beginIndex++;
					newDNI.append(numNIE);

				}
			}

			// check if the last character is a letter
			if (Character.isLetter(dni.charAt(endIndex))) {

				//lastCapitalLetter = dni.substring(endIndex, dni.length());
				endIndex--;

			}

			if (!isPassport) {
						
				newDNI.append(dni.substring(beginIndex, endIndex+1));
				
				if( !checkIfAllAreDigits(newDNI.toString())){
					isPassport=true;
					newDNI.replace(0, newDNI.length(), "passport");
				};

				if (!isPassport) {

					numDNI = addZeros(newDNI.toString());
					calculatedLastCapitalLetter = calculateDNILetter(numDNI);

					newDNI.append(calculatedLastCapitalLetter);

				}

			}

		}
		if(firstCapitalLetter!=null && !isPassport) {
			newDNI.replace(0, 1, firstCapitalLetter);
		}

		return newDNI.toString();

	}

	/*   
	 * METHOD ******************************* SPECIFICATION *******************
	 * int turnLetterToNum(					| This method returns an int, that corresponds to a letter
	 * 		String letter)					| X --> 0 , Y --> 1 , Z --> 2, in other cases --> 9
	 * 
	 */
	public int turnLetterToNum(String letter) {
		String letterX = "X";
		String letterY = "Y";
		String letterZ = "Z";
		if (letter.equals(letterX)) {
			return 0;
		} else if (letter.equals(letterY)) {
			return 1;
		} else if (letter.equals(letterZ)) {
			return 2;
		} else { // it's not a NIE
			return 9;
		}
	}

	/*   
	 * METHOD ******************************* SPECIFICATION *******************
	 * boolean checkIfAllAreDigits(  		| This method returns true if all characters are digits
	 * 		String characters)				| and false if don't
	 * 
	 */
	public boolean checkIfAllAreDigits(String characters) {
		for (int i = 0; i < characters.length(); i++) {
			if (!Character.isDigit(characters.charAt(i)))
				return false;
		}
		return true;
	}

	/*   
	 * METHOD ******************************* SPECIFICATION *******************
	 * String addZeros(  					| This method add 0 in first place to the dni parameter
	 * 		String dni)						| until the dni length was 8
	 * 
	 */
	public String addZeros(String dni) {

		StringBuffer newString = new StringBuffer(dni);

		for (int i = 0; newString.length() < 8; i++) {
			newString.insert(i, "0");
		}

		return newString.toString();

	}

	/*   
	 * METHOD ******************************* SPECIFICATION *******************
	 * String calculateDNILetter( 			| This method returns the control letter corresponding
	 * 		String numDNI)					| to the DNI number
	 * 
	 */
	public String calculateDNILetter(String numDNI) {
		char[] letterDNI = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
				'H', 'L', 'C', 'K', 'E' };
		int numDNI2 = Integer.parseInt(numDNI);
		
		int substract = numDNI2 % 23;

		String letter = String.valueOf(letterDNI[substract]);
		return letter;
	}
}
