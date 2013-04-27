package com.github.apetrelli.gwtintegration.spring.context.client.locale;

import com.github.apetrelli.gwtintegration.spring.context.server.locale.LocaleService;
import com.github.apetrelli.gwtintegration.spring.context.server.requestfactory.GwtServiceLocator;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value = LocaleService.class, locator = GwtServiceLocator.class)
public interface LocaleRequest extends RequestContext {

	Request<Void> setLocale(String localeCode);
}
