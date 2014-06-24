package com.github.apetrelli.gwtintegration.deletable.locator;

import com.github.apetrelli.gwtintegration.deletable.Deletable;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A locator that manages {@link Deletable} elements.
 *
 * @param <T> The element type.
 * @param <I> The id type.
 */
public abstract class DeletableLocator<T extends Deletable, I> extends
        Locator<T, I> {

    @Override
    public boolean isLive(T domainObject) {
        return domainObject != null && !domainObject.isDeleted();
    }

    @Override
    public T find(Class<? extends T> clazz, I id) {
        return find(id);
    }

    public abstract T find(I id);
}
