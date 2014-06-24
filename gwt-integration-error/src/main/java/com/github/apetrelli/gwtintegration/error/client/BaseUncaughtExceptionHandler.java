package com.github.apetrelli.gwtintegration.error.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.web.bindery.event.shared.UmbrellaException;

/**
 * Handles client exceptions by showing an error message.
 */
public abstract class BaseUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Constructor.
     */
    public BaseUncaughtExceptionHandler() {
    }

    @Override
    public void onUncaughtException(Throwable e) {
        // Get rid of UmbrellaException
        Throwable exceptionToDisplay = getExceptionToDisplay(e);
        // Replace with your favorite message dialog, e.g. GXT's MessageBox
        log(e, exceptionToDisplay);
        show(exceptionToDisplay);
    }

    protected abstract void show(Throwable exceptionToDisplay);

    protected void log(Throwable originalException, Throwable exceptionToDisplay) {
        // Logging to GWT log, so it appears in GWT log, and jul logger, so it may be transmitted
        // even to remote loggers.
        GWT.log("Unexpected exception client side", exceptionToDisplay);
        logger.log(Level.SEVERE, "Exception happened", exceptionToDisplay);
    }

    protected Throwable getExceptionToDisplay(Throwable throwable) {
        Throwable result = throwable;
        if (throwable instanceof UmbrellaException
                && ((UmbrellaException) throwable).getCauses().size() == 1) {
            result = ((UmbrellaException) throwable).getCauses().iterator()
                    .next();
        }
        return result;
    }
}
