package com.github.apetrelli.gwtintegration.web.client.text;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;

public class NumberFormatRenderer<T extends Number> extends AbstractRenderer<T> {

	private NumberFormat format;

	public NumberFormatRenderer(NumberFormat format) {
		this.format = format;
	}

	@Override
	public String render(T object) {
		if (null == object) {
			return "";
		}
		return format.format(object);
	}

}
