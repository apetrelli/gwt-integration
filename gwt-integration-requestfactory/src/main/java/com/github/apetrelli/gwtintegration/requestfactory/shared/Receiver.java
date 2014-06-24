package com.github.apetrelli.gwtintegration.requestfactory.shared;

import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * The base request factory receiver that throws a better manageable ServerFailureException.
 *
 * @param <V>
 */
public abstract class Receiver<V> extends
        com.google.web.bindery.requestfactory.shared.Receiver<V> {
    @Override
    public void onFailure(ServerFailure error) {
        if (error.isFatal()) {
            throw new ServerFailureException((ServerFailure) error);
        }
    }
}
