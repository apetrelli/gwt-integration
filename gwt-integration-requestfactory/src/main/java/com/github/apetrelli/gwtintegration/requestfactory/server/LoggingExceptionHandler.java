package com.github.apetrelli.gwtintegration.requestfactory.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Extension of ExceptionHandler.
 * Handles an exception produced while processing a request.
 *
 */
public class LoggingExceptionHandler implements ExceptionHandler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public ServerFailure createServerFailure(Throwable throwable) {
		if (throwable != null) {
			log.error("Server failure on request factory", throwable);
			// Writing the stack trace to the client is a bad idea, don't do it.
			return new ServerFailure("A server error occurred", null, null, true);
		}
		log.error("Unrecognized server failure on request factory");
		return new ServerFailure("An unrecognized server error occurred", null, null, true);
	}

}
