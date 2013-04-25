package com.github.apetrelli.gwtintegration.editor.client.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.Window;
import com.google.gwt.validation.client.impl.Validation;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public abstract class BaseEditorWorkflow<V, R extends RequestContext, T extends BaseProxy, E extends Editor<T>> {
	
	private final class ConfigurableReceiver extends Receiver<V> {
		@Override
		public void onFailure(ServerFailure error) {
			onServerFailure(this, error);
		}
		
		public void callParentOnFailure(ServerFailure error) {
			super.onFailure(error);
		}

		@Override
		public void onConstraintViolation(
				Set<ConstraintViolation<?>> violations) {
			onServerViolations(violations);
		}

		@Override
		public void onSuccess(V response) {
			currentRequestContext = getNewRequestContext();
			currentEntity = currentRequestContext.edit(currentEntity);
			editCurrentEntity();
			currentRequest = null;
			process(response);
		}
	}

	protected T currentEntity;
	
	protected R currentRequestContext;
	
	private Request<V> currentRequest;

	private RequestFactory requestFactory;
	
	private RequestFactoryEditorDriver<T, E> driver;
	
	private E editor;

	/**
	 * @param driver
	 */
	public BaseEditorWorkflow(RequestFactory requestFactory,
			RequestFactoryEditorDriver<T, E> driver, E editor) {
		this.requestFactory = requestFactory;
		this.driver = driver;
		this.editor = editor;
	}
	
	public void start() {
		initialize();
		createAndEdit();
	}
	
	public void execute() {
		@SuppressWarnings("unchecked")
		R request = (R) driver.flush();
		if (driver.hasErrors()) {
			onErrors();
			return;
		}
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set<ConstraintViolation<?>> violations = (Set) validator.validate(currentEntity);
		if (violations != null && !violations.isEmpty()) {
			onClientViolations(violations);
			return;
		}
		if (currentRequest == null) {
			currentRequest = getNewExecuteRequest(request, currentEntity);
		}
		currentRequest.fire(new ConfigurableReceiver());
	}
	
	public T getCurrentEntity() {
		return currentEntity;
	}
	
	protected abstract Request<V> getNewExecuteRequest(R requestContext, T entity);

	protected abstract R getNewRequestContext();

	protected abstract Class<T> getEntityProxyClass();
	
	protected abstract void process(V response);

	protected void initialize() {
		currentRequestContext = getNewRequestContext();
		driver.initialize(requestFactory, editor);
	}

	protected void onErrors() {
		Window.alert("Errors");
	}

	protected void onClientViolations(Set<ConstraintViolation<?>> violations) {
		driver.setConstraintViolations(violations);
	}

	protected void onServerViolations(
			Set<ConstraintViolation<?>> violations) {
		driver.setConstraintViolations(violations);
	}

	protected void createAndEdit() {
		createCurrentEntity();
		editCurrentEntity();
	}

	protected String[] getPaths() {
		return driver.getPaths();
	}

	protected void createCurrentEntity() {
		currentEntity = currentRequestContext.create(getEntityProxyClass());
	}

	protected void editCurrentEntity() {
		driver.edit(currentEntity, currentRequestContext);
		driver.setConstraintViolations(null);
	}

	protected void onServerFailure(Receiver<V> receiver, ServerFailure error) {
		((ConfigurableReceiver) receiver).callParentOnFailure(error);
	}
}
