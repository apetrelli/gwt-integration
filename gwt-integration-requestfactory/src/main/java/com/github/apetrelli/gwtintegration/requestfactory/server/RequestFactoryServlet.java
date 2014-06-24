package com.github.apetrelli.gwtintegration.requestfactory.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.Logging;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

/**
 * Extension of RequestFactoryServlet.
 *
 */
public class RequestFactoryServlet extends HttpServlet {
    private static final boolean DUMP_PAYLOAD = Boolean
            .getBoolean("gwt.rpc.dumpPayload");
    private static final String JSON_CHARSET = "UTF-8";
    private static final String JSON_CONTENT_TYPE = "application/json";

    private static final long serialVersionUID = 1L;

    private final SimpleRequestProcessor processor;

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Constructor.
     */
    public RequestFactoryServlet() {
        this(new LoggingExceptionHandler());
    }

    /**
     * Constructor.
     *
     * @param exceptionHandler
     *            Handles an exception produced while processing a request
     * @param serviceDecorators
     *            List of services to management decorator.
     */
    public RequestFactoryServlet(ExceptionHandler exceptionHandler,
            ServiceLayerDecorator... serviceDecorators) {
        SimpleRequestProcessor processor = createRequestProcessor(
                exceptionHandler, serviceDecorators);
        this.processor = processor;
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

        // No new code should be placed outside of this try block.
        ensureConfig();
        String jsonRequestString = RPCServletUtils.readContent(request,
                JSON_CONTENT_TYPE, JSON_CHARSET);
        if (DUMP_PAYLOAD) {
            System.out.println(">>> " + jsonRequestString);
        }

        try {
            String payload = process(processor, request, response, jsonRequestString);
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
    }

    protected SimpleRequestProcessor createRequestProcessor(
            ExceptionHandler exceptionHandler,
            ServiceLayerDecorator... serviceDecorators) {
        SimpleRequestProcessor processor = new SimpleRequestProcessor(
                ServiceLayer.create(serviceDecorators));
        processor.setExceptionHandler(exceptionHandler);
        return processor;
    }

    protected String process(SimpleRequestProcessor processor,
            HttpServletRequest request, HttpServletResponse response,
            String jsonRequestString) {
        return processor.process(jsonRequestString);
    }

    private void ensureConfig() {
        String symbolMapsDirectory = getServletConfig().getInitParameter(
                "symbolMapsDirectory");
        if (symbolMapsDirectory != null) {
            Logging.setSymbolMapsDirectory(symbolMapsDirectory);
        }
    }
}
