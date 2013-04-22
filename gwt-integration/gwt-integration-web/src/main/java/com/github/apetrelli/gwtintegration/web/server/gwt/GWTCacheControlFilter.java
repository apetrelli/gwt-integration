package com.github.apetrelli.gwtintegration.web.server.gwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Code copied from:
 * http://seewah.blogspot.it/2009/02/gwt-tips-2-nocachejs-getting-cached-in.html
 * 
 * {@link Filter} to add cache control headers for GWT generated files to ensure
 * that the correct files get cached.
 * 
 * @author See Wah Cheng
 * @created 24 Feb 2009
 */
public class GWTCacheControlFilter implements Filter {

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();

		if (requestURI.contains(".nocache.")) {
			Date now = new Date();
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setDateHeader("Date", now.getTime());
			// one day old
			httpResponse.setDateHeader("Expires", now.getTime() - 86400000L);
			httpResponse.setHeader("Pragma", "no-cache");
			httpResponse.setHeader("Cache-control",
					"no-cache, no-store, must-revalidate");
		}

		filterChain.doFilter(request, response);
	}
}