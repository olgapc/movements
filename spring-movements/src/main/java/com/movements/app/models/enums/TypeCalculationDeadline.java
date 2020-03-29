package com.movements.app.models.enums;

public enum TypeCalculationDeadline {

	DAYS("Dies"), MONTHLY("Mensual"), WEEKLY("Setmanal"), DAILY("Diari"), ANNUAL("Anual");

	private String displayValue;

	TypeCalculationDeadline(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}


}
