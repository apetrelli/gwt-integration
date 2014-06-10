package com.github.apetrelli.gwtintegration.remotelogging.server;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.logging.server.RemoteLoggingServiceUtil;
import com.google.gwt.logging.server.RemoteLoggingServiceUtil.RemoteLoggingException;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of {@link RemoteLoggingService} that loads symbol maps from classpath.
 */
public class RemoteLoggingServiceImpl extends RemoteServiceServlet implements
		RemoteLoggingService {

	private static final long serialVersionUID = -420874926904940337L;

	private StackTraceDeobfuscator deobfuscator;

	private static Logger logger = Logger.getLogger(RemoteServiceServlet.class
			.getName());
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String symbolMapsDir = config.getInitParameter("SYMBOL_MAPS_DIR");
		if (symbolMapsDir != null) {
			deobfuscator = new StackTraceDeobfuscator(symbolMapsDir, config.getServletContext());
		}
	}

	@Override
	public final String logOnServer(LogRecord lr) {
		String strongName = getPermutationStrongName();
		try {
			RemoteLoggingServiceUtil.logOnServer(lr, strongName, deobfuscator,
					null);
		} catch (RemoteLoggingException e) {
			logger.log(Level.SEVERE, "Remote logging failed", e);
			return "Remote logging failed, check stack trace for details.";
		}
		return null;
	}
}
