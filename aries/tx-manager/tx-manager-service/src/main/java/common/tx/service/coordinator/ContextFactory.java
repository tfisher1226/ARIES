package common.tx.service.coordinator;

import common.tx.exception.SystemException;
import common.tx.model.context.CoordinationContext;
import common.tx.model.context.CoordinationContextType;


public interface ContextFactory {
	
    /**
     * Called when a context factory is added to a context factory mapper. This method will be called multiple times
     * if the context factory is added to multiple context factory mappers or to the same context mapper with different
     * protocol identifiers.
     *
     * @param coordinationTypeURI the coordination type uri
     */
    public void install(String coordinationTypeURI);

    /**
     * Creates a coordination context.
     *
     * @param coordinationTypeURI the coordination type uri
     * @param expires the expire date/time for the returned context, can be null
     * @param currentContext the current coordination context, can be null
     *
     * @return the created coordination context
     *
     * @throws com.arjuna.wsc.InvalidCreateParametersException if a parameter passed is invalid
     *         this activity identifier
     */
    public CoordinationContext create(String coordinationTypeURI, Long expires, CoordinationContextType currentContext, boolean isSecure) throws SystemException;

    /**
     * Called when a context factory is removed from a context factory mapper. This method will be called multiple
     * times if the context factory is removed from multiple context factory mappers or from the same context factory
     * mapper with different coordination type uris.
     *
     * @param coordinationTypeURI the coordination type uri
     */
    public void uninstall(String coordinationTypeURI);

}
