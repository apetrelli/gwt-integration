package com.github.apetrelli.gwtintegration.web.client.ui;

import java.text.ParseException;

import com.google.gwt.user.client.TakesValue;

public interface TakesParseableValue<T> extends TakesValue<T> {

	T getValueOrThrow() throws ParseException;
	
	String getUnparsedText();
}
