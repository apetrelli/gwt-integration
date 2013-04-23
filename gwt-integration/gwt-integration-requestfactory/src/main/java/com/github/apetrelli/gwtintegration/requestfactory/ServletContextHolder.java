package com.github.apetrelli.gwtintegration.requestfactory;

import javax.servlet.ServletContext;

public class ServletContextHolder {

	private static ServletContext servletContext;
	
	public static void setServletContext(ServletContext servletContext) {
		ServletContextHolder.servletContext = servletContext;
	}
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
}
