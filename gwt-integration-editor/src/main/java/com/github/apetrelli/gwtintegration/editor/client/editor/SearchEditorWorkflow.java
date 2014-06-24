package com.github.apetrelli.gwtintegration.editor.client.editor;


import java.util.List;

import com.github.apetrelli.gwtintegration.editor.client.requestfactory.SearchRequest;
import com.google.gwt.editor.client.Editor;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
public abstract class SearchEditorWorkflow<T extends EntityProxy, R extends SearchRequest<T, F>, E extends Editor<F>, F extends ValueProxy> extends BaseEditorWorkflow<List<T>, R, F, E> {

    /**
     * @param driver
     */
    public SearchEditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<F, E> driver, E editor) {
        super(requestFactory, driver, editor);
    }

    public SearchEditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<F, E> driver, E editor,
            ConstraintViolationDisplayer genericDisplayer) {
        super(requestFactory, driver, editor, genericDisplayer);
    }

    public void search() {
        execute();
    }

    @Override
    protected Request<List<T>> getNewExecuteRequest(R requestContext, F entity) {
        return getNewSearchRequest(requestContext, entity);
    }

    @Override
    protected void process(List<T> response) {
        processResult(response);
    }

    protected Request<List<T>> getNewSearchRequest(R requestContext, F filter) {
        return requestContext.search(filter);
    }

    protected abstract void processResult(List<T> response);
}
