package com.github.apetrelli.gwtintegration.spring.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.github.apetrelli.gwtintegration.web.server.locale.LocaleLocator;

public class SessionLocaleHolderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LocaleLocator.setHolder(new SessionLocaleHolder());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LocaleLocator.setHolder(null);
	}

}
