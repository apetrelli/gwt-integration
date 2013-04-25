package com.github.apetrelli.gwtintegration.remotelogging;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * Deobfuscator that loads symbol maps from classpath.
 *
 */
public class StackTraceDeobfuscator extends com.google.gwt.logging.server.StackTraceDeobfuscator {

	private String symbolMapsDirectory;
	
	private ServletContext servletContext;
	
	/**
	 * Constructor.
	 * 
	 * @param symbolMapsDirectory The main directory containing symbol maps.
	 * @param servletContext The Servlet context.
	 */
	public StackTraceDeobfuscator(String symbolMapsDirectory, ServletContext servletContext) {
		super(symbolMapsDirectory);
		this.servletContext = servletContext;
	}
	
	@Override
	public void setSymbolMapsDirectory(String symbolMapsDirectory) {
		this.symbolMapsDirectory = symbolMapsDirectory;
	}

	@Override
	protected InputStream getSymbolMapInputStream(String permutationStrongName)
			throws IOException {
		InputStream stream = servletContext.getResourceAsStream(symbolMapsDirectory + "/" + permutationStrongName + ".symbolMap");
		if (stream == null) {
			throw new IOException("Cannot find symbol map for " + permutationStrongName);
		}
		return stream;
	}
}
