package com.movements.app.editors;

import java.beans.PropertyEditorSupport;

public class LowerCaseEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		setValue(text.toLowerCase().trim());
	}

}
