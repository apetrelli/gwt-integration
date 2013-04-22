package com.github.apetrelli.gwtintegration.web.server.gwt;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;

public class SessionFixationProtectionRequestProcessor extends SimpleRequestProcessor {
	
	private SessionAuthenticationStrategy strategy;
	
	private AuthenticationTrustResolver authenticationTrustResolver;

	public SessionFixationProtectionRequestProcessor(ServiceLayer serviceLayer) {
		super(serviceLayer);
		strategy = new SessionFixationProtectionStrategy();
		authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	}

	@Override
	public String process(String payload) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean loggedIn = authentication != null && !authenticationTrustResolver.isAnonymous(authentication);
		final String retValue = super.process(payload);
		if (!loggedIn) {
			authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && !authenticationTrustResolver.isAnonymous(authentication)) {
				strategy.onAuthentication(authentication,
						CustomRequestFactoryServlet.getThreadLocalRequest(),
						CustomRequestFactoryServlet.getThreadLocalResponse());
			}
		}
		return retValue;
	}
}
