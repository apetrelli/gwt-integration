package com.github.apetrelli.gwtintegration.web.client.requestfactory;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

public interface SearchRequest<T extends EntityProxy, F extends ValueProxy> extends RequestContext {

	Request<List<T>> search(F filter);
}
