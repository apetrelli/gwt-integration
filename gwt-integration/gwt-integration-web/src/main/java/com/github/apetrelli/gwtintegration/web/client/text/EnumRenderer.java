package com.github.apetrelli.gwtintegration.web.client.text;

import com.google.gwt.text.shared.AbstractRenderer;

public class EnumRenderer<T extends Enum<T>> extends AbstractRenderer<T> {

	@Override
	public String render(T object) {
		return object != null ? object.toString() : "";
	}

}
