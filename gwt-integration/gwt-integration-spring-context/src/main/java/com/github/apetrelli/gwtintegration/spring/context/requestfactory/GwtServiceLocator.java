package com.github.apetrelli.gwtintegration.spring.context.requestfactory;

import com.github.apetrelli.gwtintegration.spring.context.ApplicationContextHolderLocator;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Locator for GWTService.
 */
public class GwtServiceLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		return ApplicationContextHolderLocator.getHolder().getApplicationContext().getBean(clazz);
	}
}
