package common.tx.service.registration;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import common.tx.InstanceIdentifier;
import common.tx.exception.AlreadyRegisteredException;
import common.tx.exception.InvalidProtocolException;
import common.tx.exception.InvalidStateException;
import common.tx.exception.NoActivityException;


public interface Registrar {
	
    /**
     * Called when a registrar is added to a register mapper. This method will be called multiple times if the
     * registrar is added to multiple register mappers or to the same register mapper with different protocol
     * identifiers.
     */
    public void install(String protocolIdentifier);

    /**
     * Called when a registrar is removed from a register mapper. This method will be called multiple times if the
     * registrar is removed from multiple register mappers or from the same register mapper with different protocol
     * identifiers.
     */
    public void uninstall(final String protocolIdentifier);

    
    /**
     * Registers the interest of participant in a particular protocol.
     *
     * @param participantProtocolService the port reference of the participant protocol service
     * @param protocolIdentifier the protocol identifier
     * @param instanceIdentifier the instance identifier, this may be null
     *
     * @return the port reference of the coordinator protocol service
     *
     * @throws com.arjuna.wsc.AlreadyRegisteredException if the participant is already registered for this coordination protocol under
     *         this activity identifier
     * @throws com.arjuna.wsc.InvalidProtocolException if the coordination protocol is not supported
     * @throws com.arjuna.wsc.InvalidStateException if the state of the coordinator no longer allows registration for this
     *         coordination protocol
     * @throws com.arjuna.wsc.NoActivityException if the actvity does not exist
     */
    public W3CEndpointReference register(W3CEndpointReference participantProtocolService, String protocolIdentifier, InstanceIdentifier instanceIdentifier, String participantId, boolean isSecure)
        throws AlreadyRegisteredException, InvalidProtocolException, InvalidStateException, NoActivityException;

}
