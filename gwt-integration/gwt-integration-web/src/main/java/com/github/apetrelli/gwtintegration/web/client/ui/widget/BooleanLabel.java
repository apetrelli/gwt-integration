package com.github.apetrelli.gwtintegration.web.client.ui.widget;


import com.github.apetrelli.gwtintegration.web.client.text.BooleanRenderer;
import com.google.gwt.user.client.ui.ValueLabel;

public class BooleanLabel extends ValueLabel<Boolean> {

	/**
	 * @param renderer
	 */
	public BooleanLabel() {
		super(BooleanRenderer.instance());
	}

}
