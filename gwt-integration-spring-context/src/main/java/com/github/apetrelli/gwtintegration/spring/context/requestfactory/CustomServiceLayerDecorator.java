package com.github.apetrelli.gwtintegration.spring.context.requestfactory;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.springframework.context.ApplicationContext;

import com.github.apetrelli.gwtintegration.spring.context.ApplicationContextHolderLocator;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;

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
		validator = ApplicationContextHolderLocator.getHolder()
				.getApplicationContext().getBean(ValidatorFactory.class)
				.getValidator();
	}

	@Override
	public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
		ApplicationContext context = ApplicationContextHolderLocator
				.getHolder().getApplicationContext();
		return context.getBean(clazz);
	}
	
	@Override
	public <T extends Object> java.util.Set<javax.validation.ConstraintViolation<T>> validate(T domainObject) {
		return validator.validate(domainObject, Default.class);
	}
}
