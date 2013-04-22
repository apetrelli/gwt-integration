package com.github.apetrelli.gwtintegration.web.server.gwt;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.github.apetrelli.gwtintegration.web.server.locale.LocaleLocator;
import com.github.apetrelli.gwtintegration.web.server.locale.SessionLocaleHolder;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.Logging;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

/**
 * Extension of RequestFactoryServlet.
 * 
 */
public class CustomRequestFactoryServlet extends RequestFactoryServlet {
	private static final boolean DUMP_PAYLOAD = Boolean
			.getBoolean("gwt.rpc.dumpPayload");
	private static final String JSON_CHARSET = "UTF-8";
	private static final String JSON_CONTENT_TYPE = "application/json";
	/**
	 * These ThreadLocals are used to allow service objects to obtain access to
	 * the HTTP transaction.
	 */
	private static final ThreadLocal<ServletContext> perThreadContext = new ThreadLocal<ServletContext>();
	private static final ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> perThreadResponse = new ThreadLocal<HttpServletResponse>();

	private static final long serialVersionUID = 1L;

	private CustomServiceLayerDecorator decorator;

	private final SessionFixationProtectionRequestProcessor processor;

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor.
	 */
	public CustomRequestFactoryServlet() {
		this(new LoggingExceptionHandler(), new CustomServiceLayerDecorator());
	}

	/**
	 * Returns the thread-local {@link HttpServletRequest}.
	 * 
	 * @return an {@link HttpServletRequest} instance
	 */
	public static HttpServletRequest getThreadLocalRequest() {
		return perThreadRequest.get();
	}

	/**
	 * Returns the thread-local {@link HttpServletResponse}.
	 * 
	 * @return an {@link HttpServletResponse} instance
	 */
	public static HttpServletResponse getThreadLocalResponse() {
		return perThreadResponse.get();
	}

	/**
	 * Returns the thread-local {@link ServletContext}
	 * 
	 * @return the {@link ServletContext} associated with this servlet
	 */
	public static ServletContext getThreadLocalServletContext() {
		return perThreadContext.get();
	}

	/**
	 * Constructor.
	 * 
	 * @param exceptionHandler
	 *            Handles an exception produced while processing a request
	 * @param serviceDecorators
	 *            List of services to management decorator.
	 */
	public CustomRequestFactoryServlet(ExceptionHandler exceptionHandler,
			ServiceLayerDecorator... serviceDecorators) {
		processor = new SessionFixationProtectionRequestProcessor(
				ServiceLayer.create(serviceDecorators));
		processor.setExceptionHandler(exceptionHandler);
		decorator = (CustomServiceLayerDecorator) serviceDecorators[0];
		LocaleLocator.setHolder(new SessionLocaleHolder());
		SLF4JBridgeHandler.install();
	}

	@Override
	public void init() throws ServletException {
		decorator.setServletContext(getServletContext());
	}

	/**
	 * Processes a POST to the server.
	 * 
	 * @param request
	 *            an {@link HttpServletRequest} instance
	 * @param response
	 *            an {@link HttpServletResponse} instance
	 * @throws IOException
	 *             if an internal I/O error occurs
	 * @throws ServletException
	 *             if an error occurs in the servlet
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		perThreadContext.set(getServletContext());
		perThreadRequest.set(request);
		perThreadResponse.set(response);

		
		// No new code should be placed outside of this try block.
		try {
			ensureConfig();
			String jsonRequestString = RPCServletUtils.readContent(request,
					JSON_CONTENT_TYPE, JSON_CHARSET);
			if (DUMP_PAYLOAD) {
				System.out.println(">>> " + jsonRequestString);
			}

			try {
				String payload = processor.process(jsonRequestString);
				if (DUMP_PAYLOAD) {
					System.out.println("<<< " + payload);
				}
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType(RequestFactory.JSON_CONTENT_TYPE_UTF8);
				// The Writer must be obtained after setting the content type
				PrintWriter writer = response.getWriter();
				writer.print(payload);
				writer.flush();
			} catch (RuntimeException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				log.error("Unexpected error", e);
			}
		} finally {
			perThreadContext.set(null);
			perThreadRequest.set(null);
			perThreadResponse.set(null);
		}
	}

	private void ensureConfig() {
		String symbolMapsDirectory = getServletConfig().getInitParameter(
				"symbolMapsDirectory");
		if (symbolMapsDirectory != null) {
			Logging.setSymbolMapsDirectory(symbolMapsDirectory);
		}
	}
}
