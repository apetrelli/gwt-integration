package com.github.apetrelli.gwtintegration.requestfactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextHolderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextHolder.setServletContext(sce.getServletContext());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContextHolder.setServletContext(null);
	}

}
