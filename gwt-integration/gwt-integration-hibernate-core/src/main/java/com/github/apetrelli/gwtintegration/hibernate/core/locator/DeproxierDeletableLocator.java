package com.github.apetrelli.gwtintegration.hibernate.core.locator;

import org.hibernate.proxy.HibernateProxy;

import com.github.apetrelli.gwtintegration.deletable.Deletable;
import com.github.apetrelli.gwtintegration.deletable.locator.DeletableLocator;

/**
 * A locator that manages {@link Deletable} elements and deproxies Hibernate proxies.
 *
 * @param <T> The element type.
 * @param <I> The id type.
 */
public abstract class DeproxierDeletableLocator<T extends Deletable, I> extends
		DeletableLocator<T, I> {
	
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
}
