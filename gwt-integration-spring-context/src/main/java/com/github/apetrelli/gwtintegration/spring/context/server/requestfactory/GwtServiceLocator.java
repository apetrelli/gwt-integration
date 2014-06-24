package com.github.apetrelli.gwtintegration.spring.context.server.requestfactory;

import com.github.apetrelli.gwtintegration.spring.context.server.ApplicationContextHolderLocator;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Locator for GWTService.
 */
public class GwtServiceLocator implements ServiceLocator {

    @Override
    public Object getInstance(Class<?> clazz) {
        return ApplicationContextHolderLocator.getHolder().getApplicationContext().getBean(clazz);
    }
}
