package com.github.apetrelli.gwtintegration.editor.client.text;

import java.text.ParseException;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Parser;

public abstract class NumberFormatParser<T extends Number> implements Parser<T> {

    private NumberFormat format;

    public NumberFormatParser(NumberFormat format) {
        this.format = format;
    }

    @Override
    public T parse(CharSequence text) throws ParseException {
        if ("".equals(text.toString())) {
            return null;
        }

        try {
            return parseString(text, format);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

    protected abstract T parseString(CharSequence text, NumberFormat format);

}
