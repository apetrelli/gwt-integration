package com.github.apetrelli.gwtintegration.error.client;

import com.github.apetrelli.gwtintegration.error.client.widget.ErrorDisplayWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

/**
 * Handles client exceptions by showing an error message.
 */
public class DialogBoxUncaughtExceptionHandler extends BaseUncaughtExceptionHandler {

    private ErrorDisplayWidget errorDisplayWidget;

    private DialogBox dialogBox;

    /**
     * Constructor.
     */
    public DialogBoxUncaughtExceptionHandler() {
        createGenericErrorDialog();
    }

    protected void show(Throwable exceptionToDisplay) {
        DialogBox dialogBox = this.dialogBox;
        errorDisplayWidget.prepare(exceptionToDisplay.getMessage());
        dialogBox.setText("Uncaught exception, probable client side error");
        dialogBox.center();
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
