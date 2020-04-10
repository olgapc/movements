package com.movements.app.models.enums;

public enum TypeNif {

	DNI("DNI"), NIE("NIE"), PASSPORT("Passaport");

	private String displayValue;

	TypeNif(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
	
}
