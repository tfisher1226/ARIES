package common.tx.handler.service;

import java.util.HashMap;
import java.util.Map;

import common.tx.service.coordinator.ContextFactory;


public class ContextFactoryMapper {
	
    private static final ContextFactoryMapper theMapper = new ContextFactoryMapper();

    public static ContextFactoryMapper getMapper() {
        return theMapper;
    }

    private final Map contextFactoryMap = new HashMap();

    
    protected ContextFactoryMapper() {
    }

    /**
     * Add a context factory for the specified coordination type.
     * @param coordinationTypeURI The coordination type.
     * @param contextFactory The context factory.
     */
    public void addContextFactory(String coordinationTypeURI, ContextFactory contextFactory) {
        synchronized (contextFactoryMap) {
            contextFactoryMap.put(coordinationTypeURI, contextFactory);
        }
        contextFactory.install(coordinationTypeURI);
    }

    /**
     * Get the context factory for the specified coordination type.
     * @param coordinationTypeURI The coordination type.
     * @return The context factory.
     */
    public ContextFactory getContextFactory(String coordinationTypeURI) {
        synchronized (contextFactoryMap) {
            return (ContextFactory)contextFactoryMap.get(coordinationTypeURI);
        }
    }

    /**
     * Remove the context factory for the specified coordination type.
     * @param coordinationTypeURI The coordination type.
     */
    public void removeContextFactory(String coordinationTypeURI) {
        Object localContextFactory = null;
        synchronized (contextFactoryMap) {
            localContextFactory = contextFactoryMap.remove(coordinationTypeURI);
        }
        if (localContextFactory != null) {
            ((ContextFactory) localContextFactory).uninstall(coordinationTypeURI);
        }
    }

	public void removeAll() {
        synchronized (contextFactoryMap) {
            contextFactoryMap.clear();
        }
	}
}
