package com.github.apetrelli.gwtintegration.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Extension of ExceptionHandler.
 * Handles an exception produced while processing a request.
 *
 */
public class SpringSecurityLoggingExceptionHandler implements ExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ServerFailure createServerFailure(Throwable throwable) {
        if (throwable != null) {
            String type = null;
            String message = "A server error occurred";
            if (throwable instanceof AccessDeniedException) {
                log.warn("Access denied, possible session expiration", throwable);
                type = "security";
                message = "Security error";
            }

            log.error("Server failure on request factory", throwable);
            // Writing the stack trace to the client is a bad idea, don't do it.
            return new ServerFailure(message, type, null, true);
        }
        log.error("Unrecognized server failure on request factory");
        return new ServerFailure("An unrecognized server error occurred", null, null, true);
    }

}
