package com.github.apetrelli.gwtintegration.editor.client.editor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.github.apetrelli.gwtintegration.requestfactory.shared.Receiver;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.validation.client.impl.Validation;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public abstract class BaseEditorWorkflow<V, R extends RequestContext, T extends BaseProxy, E extends Editor<T>> {

    private final class ConfigurableReceiver extends Receiver<V> {
        @Override
        public void onFailure(ServerFailure error) {
            try {
                onServerFailure(this, error);
            } finally {
                restart();
            }
        }

        public void callParentOnFailure(ServerFailure error) {
            super.onFailure(error);
        }

        @Override
        public void onConstraintViolation(
                Set<ConstraintViolation<?>> violations) {
            driver.setConstraintViolations(violations);
            fillGenericViolations(violations);
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

    private ConstraintViolationDisplayer genericDisplayer;

    public BaseEditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<T, E> driver, E editor) {
        this(requestFactory, driver, editor, null);
    }

    public BaseEditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<T, E> driver, E editor, ConstraintViolationDisplayer genericDisplayer) {
        this.requestFactory = requestFactory;
        this.driver = driver;
        this.editor = editor;
        this.genericDisplayer = genericDisplayer;
    }

    public void start() {
        initialize();
        createAndEdit();
    }

    public void execute() {
        @SuppressWarnings("unchecked")
        R request = (R) driver.flush();
        boolean hasErrors = driver.hasErrors();
        List<EditorError> errors = null;
        if (hasErrors) {
            errors = new ArrayList<EditorError>(driver.getErrors());
            onErrors(errors);
            // FIXME Errors cannot be displayed together with constraint violations,
            // so at the moment I have to get out of this method to show errors.
            return;
        }
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Set<ConstraintViolation<?>> violations = (Set) validator.validate(currentEntity);
        boolean hasViolations = violations != null && !violations.isEmpty();
        if (hasViolations) {
            driver.setConstraintViolations(violations);
            fillGenericViolations(violations);
            onClientViolations(violations);
        }
        if (!hasErrors && !hasViolations) {
            if (currentRequest == null) {
                currentRequest = getNewExecuteRequest(request, currentEntity);
            }
            currentRequest.fire(new ConfigurableReceiver());
        }
    }

    public T getCurrentEntity() {
        return currentEntity;
    }

    protected abstract Request<V> getNewExecuteRequest(R requestContext, T entity);

    protected abstract R getNewRequestContext();

    protected abstract Class<T> getEntityProxyClass();

    protected abstract void process(V response);

    protected void initialize() {
        currentRequest = null;
        currentRequestContext = getNewRequestContext();
        driver.initialize(requestFactory, editor);
        if (genericDisplayer != null) {
            genericDisplayer.reset();
        }
    }

    protected void onErrors(List<EditorError> errors) {
    }

    protected void onClientViolations(Set<ConstraintViolation<?>> violations) {
    }

    protected void onServerViolations(Set<ConstraintViolation<?>> violations) {
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
    
    protected void restart() {
        start();
    }

    private void fillGenericViolations(Set<ConstraintViolation<?>> violations) {
        if (genericDisplayer != null) {
            Set<ConstraintViolation<?>> genericViolations = new LinkedHashSet<ConstraintViolation<?>>();
            for (ConstraintViolation<?> violation : violations) {
            	String path = violation.getPropertyPath().toString(); // GWT does not support path iterator!
				if (path == null || path.isEmpty()) {
                    genericViolations.add(violation);
                }
            }
            genericDisplayer.setConstraintViolations(genericViolations);
        }
    }
}
