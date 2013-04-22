package com.github.apetrelli.gwtintegration.web.server.gwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Locator for GWTService.
 */
public class GwtServiceLocator implements ServiceLocator {

	HttpServletRequest request = CustomRequestFactoryServlet.getThreadLocalRequest();
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

	@Override
	public Object getInstance(Class<?> clazz) {
		return context.getBean(clazz);
	}
}
