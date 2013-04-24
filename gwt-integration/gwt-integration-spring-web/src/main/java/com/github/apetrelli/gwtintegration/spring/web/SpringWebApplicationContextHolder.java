package com.github.apetrelli.gwtintegration.spring.web;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.apetrelli.gwtintegration.spring.context.ApplicationContextHolder;

public class SpringWebApplicationContextHolder implements ApplicationContextHolder {

	private ServletContext servletContext;

	public SpringWebApplicationContextHolder(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

}
