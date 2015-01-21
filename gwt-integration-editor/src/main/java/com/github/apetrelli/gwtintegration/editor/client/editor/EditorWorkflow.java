package com.github.apetrelli.gwtintegration.editor.client.editor;

import com.github.apetrelli.gwtintegration.editor.client.requestfactory.CrudRequest;
import com.github.apetrelli.gwtintegration.requestfactory.shared.Receiver;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.History;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

public abstract class EditorWorkflow<T extends EntityProxy, R extends CrudRequest<T, I>, E extends Editor<T>, I> extends BaseEditorWorkflow<T, R, T, E> {

    private I currentId;
    
    private boolean loaded = false;
    
    /**
     * @param driver
     */
    public EditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<T, E> driver, E editor) {
        super(requestFactory, driver, editor);
    }

    public EditorWorkflow(RequestFactory requestFactory,
            RequestFactoryEditorDriver<T, E> driver, E editor,
            ConstraintViolationDisplayer genericDisplayer) {
        super(requestFactory, driver, editor, genericDisplayer);
    }

    public void start(I id) {
        loaded = false;
        currentId = id;
        initialize();
        if (id != null) {
            loadAndEdit(id);
        } else {
            loaded = true;
            createAndEdit();
        }
    }

    public void save() {
        execute();
    }

    public void delete() {
        getNewDeleteRequest(getNewRequestContext(), currentEntity).fire(new Receiver<Void>() {

            @Override
            public void onSuccess(Void response) {
                afterDelete();
            }
        });
    }

    protected void loadAndEdit(I id) {
        getNewFindRequest(currentRequestContext, id).with(getPaths()).fire(new Receiver<T>() {

            @Override
            public void onSuccess(T response) {
                if (response != null) {
                    loaded = true;
                    currentRequestContext = getNewRequestContext();
                    currentEntity = currentRequestContext.edit(response);
                    editCurrentEntity();
                } else {
                    onNoEntityFound();
                }
            }
        });
    }

    @Override
    protected Request<T> getNewExecuteRequest(R requestContext, T entity) {
        return getNewSaveRequest(requestContext, entity);
    }

    protected Request<Void> getNewDeleteRequest(R r, T entityProxy) {
        return r.delete(getEntityId(entityProxy));
    }

    protected Request<T> getNewFindRequest(R requestContext, I id) {
        return requestContext.findOne(id);
    }

    protected Request<T> getNewSaveRequest(R requestContext, T entity) {
        return requestContext.save(entity);
    }

    @Override
    protected void process(T response) {
        afterSave(response);
    }
    
    @Override
    protected void restart() {
        start(currentId);
    }
    
    @Override
    public boolean isDirty() {
        return loaded ? super.isDirty() : false;
    }
    
    protected void onNoEntityFound() {
        History.back(); // Wow this seems dangerous, however, for an exceptional case like this may have sense.
    }

    protected abstract R getNewRequestContext();

    protected abstract Class<T> getEntityProxyClass();

    protected abstract I getEntityId(T entityProxy);

    protected abstract void afterSave(T response);

    protected abstract void afterDelete();
}
