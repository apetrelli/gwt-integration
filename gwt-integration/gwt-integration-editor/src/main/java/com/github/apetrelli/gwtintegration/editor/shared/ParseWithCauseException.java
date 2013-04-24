package com.github.apetrelli.gwtintegration.editor.shared;

import java.text.ParseException;

public class ParseWithCauseException extends ParseException {

	private static final long serialVersionUID = 1057170953507365398L;

	public ParseWithCauseException(String s, int errorOffset) {
		super(s, errorOffset);
	}
	
	public ParseWithCauseException(String s, int errorOffset, Throwable cause) {
		super(s, errorOffset);
		initCause(cause);
	}
	
}
