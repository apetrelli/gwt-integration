package com.github.apetrelli.gwtintegration.web.server.gwt;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.apetrelli.gwtintegration.requestfactory.ServletContextHolder;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Locator for GWTService.
 */
public class GwtServiceLocator implements ServiceLocator {

	private ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(ServletContextHolder.getServletContext());

	@Override
	public Object getInstance(Class<?> clazz) {
		return context.getBean(clazz);
	}
}
