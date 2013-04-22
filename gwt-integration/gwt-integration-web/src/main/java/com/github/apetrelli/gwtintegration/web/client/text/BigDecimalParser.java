package com.github.apetrelli.gwtintegration.web.client.text;

import java.math.BigDecimal;
import java.text.ParseException;

import com.google.gwt.text.shared.Parser;

public class BigDecimalParser implements Parser<BigDecimal> {

	private static BigDecimalParser INSTANCE;

	public static Parser<BigDecimal> instance() {
		if (INSTANCE == null) {
			INSTANCE = new BigDecimalParser();
		}
		return INSTANCE;
	}

	protected BigDecimalParser() {
	}

	public BigDecimal parse(CharSequence object) throws ParseException {
		if ("".equals(object.toString())) {
			return null;
		}

		try {
			return new BigDecimal(object.toString());
		} catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
}