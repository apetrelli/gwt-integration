package com.github.apetrelli.gwtintegration.error.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget that displays an error message.
 */
public class ErrorDisplayWidget extends Composite {

	@UiField Label message;

	@UiField Button dismiss;

	private static ErrorDisplayWidgetUiBinder uiBinder = GWT
			.create(ErrorDisplayWidgetUiBinder.class);

	/**
	 * The UiBinder for the corresponding XML file.
	 */
	interface ErrorDisplayWidgetUiBinder extends
			UiBinder<Widget, ErrorDisplayWidget> {
	}

	/**
	 * Constructor.
	 */
	public ErrorDisplayWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Prepares the message to show.
	 * 
	 * @param message The message to show.
	 */
	public void prepare(String message) {
		this.message.setText(message);
	}

	public HasClickHandlers getDismiss() {
		return dismiss;
	}
}
