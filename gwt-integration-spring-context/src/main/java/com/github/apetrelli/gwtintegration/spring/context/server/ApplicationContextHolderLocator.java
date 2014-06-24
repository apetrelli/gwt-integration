package com.github.apetrelli.gwtintegration.spring.context.server;

public class ApplicationContextHolderLocator {

    private static ApplicationContextHolder holder;

    private ApplicationContextHolderLocator() { }

    public static ApplicationContextHolder getHolder() {
        return holder;
    }

    public static void setHolder(ApplicationContextHolder holder) {
        ApplicationContextHolderLocator.holder = holder;
    }
}
