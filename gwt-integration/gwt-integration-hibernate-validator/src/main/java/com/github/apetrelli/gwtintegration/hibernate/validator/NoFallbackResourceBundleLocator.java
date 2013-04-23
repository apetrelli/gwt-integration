// $Id: NoFallbackResourceBundleLocator.java 19573 2010-05-20 22:13:26Z hardy.ferentschik $
package com.github.apetrelli.gwtintegration.hibernate.validator;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.hibernate.validator.resourceloading.ResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Taken from the original
 * {@link org.hibernate.validator.resourceloading.PlatformResourceBundleLocator}
 * and modified the control so it does not fallback to default locale, but to
 * the root one.
 * 
 * A resource bundle locator, that loads resource bundles by simply invoking
 * <code>ResourceBundle.loadBundle(...)</code>.
 * 
 * @author Hardy Ferentschik
 * @author Gunnar Morling
 */
public class NoFallbackResourceBundleLocator implements ResourceBundleLocator {

	private static final Logger log = LoggerFactory
			.getLogger(NoFallbackResourceBundleLocator.class);
	private String bundleName;

	public NoFallbackResourceBundleLocator(String bundleName) {
		this.bundleName = bundleName;
	}

	/**
	 * Search current thread classloader for the resource bundle. If not found,
	 * search validator (this) classloader.
	 * 
	 * @param locale
	 *            The locale of the bundle to load.
	 * 
	 * @return the resource bundle or <code>null</code> if none is found.
	 */
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle rb = null;
		ClassLoader classLoader = GetClassLoader.fromContext();
		if (classLoader != null) {
			rb = loadBundle(classLoader, locale, bundleName
					+ " not found by thread local classloader");
		}
		if (rb == null) {
			classLoader = GetClassLoader
					.fromClass(NoFallbackResourceBundleLocator.class);
			rb = loadBundle(classLoader, locale, bundleName
					+ " not found by validator classloader");
		}
		if (rb != null) {
			log.debug("{} found.", bundleName);
		} else {
			log.debug("{} not found.", bundleName);
		}
		return rb;
	}

	private static class GetClassLoader implements
			PrivilegedAction<ClassLoader> {
		private final Class<?> clazz;

		public static ClassLoader fromContext() {
			final GetClassLoader action = new GetClassLoader(null);
			if (System.getSecurityManager() != null) {
				return AccessController.doPrivileged(action);
			} else {
				return action.run();
			}
		}

		public static ClassLoader fromClass(Class<?> clazz) {
			if (clazz == null) {
				throw new IllegalArgumentException("Class is null");
			}
			final GetClassLoader action = new GetClassLoader(clazz);
			if (System.getSecurityManager() != null) {
				return AccessController.doPrivileged(action);
			} else {
				return action.run();
			}
		}

		private GetClassLoader(Class<?> clazz) {
			this.clazz = clazz;
		}

		public ClassLoader run() {
			if (clazz != null) {
				return clazz.getClassLoader();
			} else {
				return Thread.currentThread().getContextClassLoader();
			}
		}
	}

	private ResourceBundle loadBundle(ClassLoader classLoader, Locale locale,
			String message) {
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle
					.getBundle(
							bundleName,
							locale,
							classLoader,
							ResourceBundle.Control
									.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
		} catch (MissingResourceException e) {
			log.trace(message);
		}
		return rb;
	}
}
