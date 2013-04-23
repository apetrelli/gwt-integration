package com.github.apetrelli.gwtintegration.web.server.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class Slf4jBridgeInstallListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SLF4JBridgeHandler.install();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Does nothing.
	}

}
