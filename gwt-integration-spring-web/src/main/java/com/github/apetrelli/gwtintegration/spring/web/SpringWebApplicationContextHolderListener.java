package com.github.apetrelli.gwtintegration.spring.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.github.apetrelli.gwtintegration.spring.context.ApplicationContextHolderLocator;

public class SpringWebApplicationContextHolderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContextHolderLocator.setHolder(new SpringWebApplicationContextHolder(sce.getServletContext()));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ApplicationContextHolderLocator.setHolder(null);
	}

}
