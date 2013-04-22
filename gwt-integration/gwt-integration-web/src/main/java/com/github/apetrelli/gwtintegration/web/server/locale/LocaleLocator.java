package com.github.apetrelli.gwtintegration.web.server.locale;

public class LocaleLocator {

	private LocaleLocator() {
	}
	
	private static LocaleHolder holder;

	public static LocaleHolder getHolder() {
		return holder;
	}
	
	public static void setHolder(LocaleHolder holder) {
		LocaleLocator.holder = holder;
	}
}
