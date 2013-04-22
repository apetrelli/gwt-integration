package com.github.apetrelli.gwtintegration.web.client.text;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.shared.AbstractRenderer;

public class DateRenderer extends AbstractRenderer<Date> {

	private DateTimeFormat format;

	public DateRenderer(DateTimeFormat format) {
		this.format = format;
	}

	@Override
	public String render(Date object) {
		return object != null ? format.format(object) : "";
	}
}
