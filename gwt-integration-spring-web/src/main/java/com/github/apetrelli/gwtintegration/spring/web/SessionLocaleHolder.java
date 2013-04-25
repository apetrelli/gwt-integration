package com.github.apetrelli.gwtintegration.spring.web;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.github.apetrelli.gwtintegration.web.server.locale.LocaleHolder;

@Component
public class SessionLocaleHolder implements LocaleHolder {

	private static final String ATTRIBUTE_NAME = SessionLocaleHolder.class
			.getName() + ".ATTRIBUTE_NAME";

	@Override
	public void setLocale(Locale locale) {
		RequestContextHolder.getRequestAttributes().setAttribute(
				ATTRIBUTE_NAME, locale, RequestAttributes.SCOPE_SESSION);
	}

	@Override
	public Locale getLocale() {
		Locale locale = (Locale) RequestContextHolder.getRequestAttributes()
				.getAttribute(ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION);
		if (locale == null) {
			locale = Locale.ROOT;
		}
		return locale;
	}

}
