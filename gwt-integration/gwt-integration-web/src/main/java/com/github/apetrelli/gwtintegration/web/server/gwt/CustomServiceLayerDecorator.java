package com.github.apetrelli.gwtintegration.web.server.gwt;

import javax.servlet.ServletContext;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Extension of ServiceLayerDecorator.
 * Create custom locator.
 *
 */
public class CustomServiceLayerDecorator extends ServiceLayerDecorator {
	
	private Validator validator;
	
	/**
	 * Constructor.
	 */
	public CustomServiceLayerDecorator() {
	}
	
	public void setServletContext(ServletContext servletContext) {
		validator = WebApplicationContextUtils
				.getWebApplicationContext(servletContext)
				.getBean(ValidatorFactory.class).getValidator();
	}

	@Override
	public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(CustomRequestFactoryServlet.getThreadLocalServletContext());
		return context.getBean(clazz);
	}
	
	@Override
	public <T extends Object> java.util.Set<javax.validation.ConstraintViolation<T>> validate(T domainObject) {
		return validator.validate(domainObject, Default.class);
	}
}
