package com.github.apetrelli.gwtintegration.requestfactory.shared;

import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Generic server failure exception.
 */
public class ServerFailureException extends RuntimeException {

    private static final long serialVersionUID = 8385106541717533628L;

    private ServerFailure failure;

    /**
     * Constructor.
     *
     * @param failure The failure.
     */
    public ServerFailureException(ServerFailure failure) {
        super(failure.getMessage());
        this.failure = failure;
    }

    public ServerFailure getFailure() {
        return failure;
    }
}
