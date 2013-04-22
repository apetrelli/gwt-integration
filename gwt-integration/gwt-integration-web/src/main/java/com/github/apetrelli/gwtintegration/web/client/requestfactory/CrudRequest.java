package com.github.apetrelli.gwtintegration.web.client.requestfactory;

import java.util.Set;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface CrudRequest<T extends EntityProxy, I> extends RequestContext {

	Request<T> findOne(I id);
	
	Request<T> save(T entityProxy);
	
	Request<Void> delete(I id);
	
	Request<Void> deleteAll(Set<T> items);
}
