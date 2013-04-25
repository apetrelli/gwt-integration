package com.github.apetrelli.gwtintegration.editor.client.text;

import com.google.gwt.i18n.client.NumberFormat;

public class IntegerParser extends NumberFormatParser<Integer>{

	public IntegerParser(NumberFormat format) {
		super(format);
	}

	@Override
	protected Integer parseString(CharSequence text, NumberFormat format) {
		return (int) Math.rint(format.parse(text.toString()));
	}

}
