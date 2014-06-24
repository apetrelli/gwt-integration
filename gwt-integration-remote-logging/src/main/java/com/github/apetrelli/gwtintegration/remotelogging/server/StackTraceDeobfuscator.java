package com.github.apetrelli.gwtintegration.remotelogging.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * Deobfuscator that loads symbol maps from classpath.
 *
 */
public class StackTraceDeobfuscator extends com.google.gwt.core.server.StackTraceDeobfuscator {

    private String symbolMapsDirectory;

    private ServletContext servletContext;

    /**
     * Constructor.
     *
     * @param symbolMapsDirectory The main directory containing symbol maps.
     * @param servletContext The Servlet context.
     */
    public StackTraceDeobfuscator(String symbolMapsDirectory, ServletContext servletContext) {
        this.symbolMapsDirectory = symbolMapsDirectory;
        this.servletContext = servletContext;
    }

    @Override
    protected InputStream openInputStream(String fileName) throws IOException {
        InputStream stream = servletContext.getResourceAsStream(symbolMapsDirectory + "/" + fileName);
        if (stream == null) {
            throw new IOException("Cannot find symbol map file: " + fileName);
        }
        return stream;
    }
}
