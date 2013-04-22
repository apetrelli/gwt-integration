package com.github.apetrelli.gwtintegration.web.server.gwt;

import org.hibernate.proxy.HibernateProxy;

import com.github.apetrelli.gwtintegration.jpa.Deletable;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public T find(Class<? extends T> clazz, I id) {
		T retValue = find(id);
		if (retValue instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) retValue;
			retValue = (T) proxy.writeReplace();
		}
		return retValue;
	}

	public abstract T find(I id);
}
