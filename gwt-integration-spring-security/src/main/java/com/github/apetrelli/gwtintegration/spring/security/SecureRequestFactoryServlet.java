package com.github.apetrelli.gwtintegration.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.github.apetrelli.gwtintegration.requestfactory.server.RequestFactoryServlet;
import com.github.apetrelli.gwtintegration.spring.context.requestfactory.CustomServiceLayerDecorator;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;

/**
 * Extension of RequestFactoryServlet.
 * 
 */
public class SecureRequestFactoryServlet extends RequestFactoryServlet {
	private static final long serialVersionUID = 1L;
	
	private SessionAuthenticationStrategy strategy;
	
	private AuthenticationTrustResolver authenticationTrustResolver;

	/**
	 * Constructor.
	 */
	public SecureRequestFactoryServlet() {
		this(new SpringSecurityLoggingExceptionHandler(), new CustomServiceLayerDecorator());
	}

	/**
	 * Constructor.
	 * 
	 * @param exceptionHandler
	 *            Handles an exception produced while processing a request
	 * @param serviceDecorators
	 *            List of services to management decorator.
	 */
	public SecureRequestFactoryServlet(ExceptionHandler exceptionHandler,
			ServiceLayerDecorator... serviceDecorators) {
		super(exceptionHandler, serviceDecorators);
		strategy = new SessionFixationProtectionStrategy();
		authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	}

	protected String process(SimpleRequestProcessor processor,
			HttpServletRequest request, HttpServletResponse response,
			String jsonRequestString) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean loggedIn = authentication != null && !authenticationTrustResolver.isAnonymous(authentication);
		String payload = processor.process(jsonRequestString);
		if (!loggedIn) {
			authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && !authenticationTrustResolver.isAnonymous(authentication)) {
				strategy.onAuthentication(authentication,
						request,
						response);
			}
		}
		return payload;
	}
}
