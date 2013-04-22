package com.github.apetrelli.gwtintegration.web.client.text;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

public class BigDecimalRenderer extends AbstractRenderer<BigDecimal> {
	private static BigDecimalRenderer INSTANCE;

	public static Renderer<BigDecimal> instance() {
		if (INSTANCE == null) {
			INSTANCE = new BigDecimalRenderer();
		}
		return INSTANCE;
	}

	protected BigDecimalRenderer() {
	}

	public String render(BigDecimal object) {
		if (null == object) {
			return "";
		}

		return NumberFormat.getDecimalFormat().format(object);
	}
}