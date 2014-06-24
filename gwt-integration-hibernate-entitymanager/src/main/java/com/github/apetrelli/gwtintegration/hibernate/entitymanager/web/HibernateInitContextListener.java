package com.github.apetrelli.gwtintegration.hibernate.entitymanager.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.ejb.HibernatePersistence;

/**
 * Listner of Hibernate Init Context
 */
public class HibernateInitContextListener implements ServletContextListener {

    /**
     *  Determine the list of persistence providers available in the runtime environment.
     */
    private final class HibernatePersistenceProviderResolver implements
            PersistenceProviderResolver {

        private List<PersistenceProvider> providers;

        public HibernatePersistenceProviderResolver() {
            providers = new ArrayList<PersistenceProvider>();
            providers.add(new HibernatePersistence());
        }
        @Override
        public List<PersistenceProvider> getPersistenceProviders() {
            return providers;
        }

        @Override
        public void clearCachedProviders() {
            // Who cares?
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PersistenceProviderResolverHolder.setPersistenceProviderResolver(new HibernatePersistenceProviderResolver());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Does nothing.
    }

}
