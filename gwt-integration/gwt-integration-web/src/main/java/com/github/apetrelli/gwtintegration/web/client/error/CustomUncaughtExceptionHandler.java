package com.github.apetrelli.gwtintegration.web.client.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.web.bindery.event.shared.UmbrellaException;

/**
 * Handles client exceptions by showing an error message.
 */
public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private ErrorDisplayWidget errorDisplayWidget;

	private DialogBox dialogBox;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Constructor.
	 */
	public CustomUncaughtExceptionHandler() {
		createGenericErrorDialog();
	}

	@Override
	public void onUncaughtException(Throwable e) {
		// Get rid of UmbrellaException
		Throwable exceptionToDisplay = getExceptionToDisplay(e);
		// Replace with your favorite message dialog, e.g. GXT's MessageBox
		GWT.log("Unexpected exception client side", exceptionToDisplay);
		DialogBox dialogBox = this.dialogBox;
		logger.log(Level.SEVERE, "Exception happened", e);
		errorDisplayWidget.prepare(exceptionToDisplay.getMessage());
		dialogBox.setText("Uncaught exception, probable client side error");
		dialogBox.center();
	}

	private static Throwable getExceptionToDisplay(Throwable throwable) {
		Throwable result = throwable;
		if (throwable instanceof UmbrellaException
				&& ((UmbrellaException) throwable).getCauses().size() == 1) {
			result = ((UmbrellaException) throwable).getCauses().iterator()
					.next();
		}
		return result;
	}

	private void createGenericErrorDialog() {
		errorDisplayWidget = new ErrorDisplayWidget();
		dialogBox = new DialogBox(true, true);
		dialogBox.setWidget(errorDisplayWidget);
		dialogBox.setHeight("40em");
		dialogBox.setWidth("80em");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setGlassEnabled(true);
		errorDisplayWidget.getDismiss().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
	}
}