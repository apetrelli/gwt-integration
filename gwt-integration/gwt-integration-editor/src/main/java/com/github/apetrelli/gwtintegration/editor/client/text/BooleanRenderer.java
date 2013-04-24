package com.github.apetrelli.gwtintegration.editor.client.text;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

public class BooleanRenderer extends AbstractRenderer<Boolean> {
	private static BooleanRenderer INSTANCE;

	public static Renderer<Boolean> instance() {
		if (INSTANCE == null) {
			INSTANCE = new BooleanRenderer();
		}
		return INSTANCE;
	}

	protected BooleanRenderer() {
	}

	public String render(Boolean object) {
		if (null == object) {
			return "";
		}

		return object.toString();
	}
}