package org.aries.tx.registration;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;

import common.tx.CoordinationConstants;
import common.tx.exception.CannotRegisterException;
import common.tx.exception.SystemException;
import common.tx.model.Event;
import common.tx.model.context.CoordinationContextType;
import common.tx.model.context.CoordinationContextType.Identifier;
import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


public class RegistrationClient extends AbstractPortTypeClient {

	private static RegistrationClient INSTANCE = new RegistrationClient();

	public static RegistrationClient getInstance() {
		return INSTANCE;
	}


	private RegistrationService service;

    private RegistrationClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.REGISTRATION_SERVICE_NAME);
		service = new RegistrationService(wsdlLocation);
    }

    
	/**
     * Register the participant in the protocol.
     * @param coordinationContext The current coordination context
     * @param messageID The messageID to use.
     * @param participantProtocolService The participant protocol service.
     * @param protocolIdentifier The protocol identifier.
     * @return The endpoint reference of the coordinator protocol service.
     * @throws com.arjuna.wsc.AlreadyRegisteredException If the participant is already registered.
     * @throws com.arjuna.wsc.InvalidProtocolException If the protocol is unsupported.
     * @throws com.arjuna.wsc.InvalidStateException If the state is invalid
     * @throws com.arjuna.wsc.NoActivityException If there is to activity context active.
     * @throws com.arjuna.webservices.SoapFault for errors during processing.
     */
    public W3CEndpointReference register(CoordinationContextType coordinationContext, W3CEndpointReference participantProtocolService, String participantId, String protocolIdentifier) throws CannotRegisterException, SystemException {
        W3CEndpointReference registrationEndpointReference = coordinationContext.getRegistrationService();
        Identifier identifier = coordinationContext.getIdentifier();
        
        //TODO TEMP only fix for now
        String transactionId = identifier.getValue().replace("urn:", "");

        try {
            RegisterType registerType = new RegisterType();
            registerType.setProtocolIdentifier(protocolIdentifier);
            registerType.setParticipantProtocolService(participantProtocolService);
    		RegistrationPortType port = service.getRegistrationPortType(registrationEndpointReference);
    		initializePort(port, transactionId, participantId, null);
            RegisterResponseType response = port.registerOperation(registerType);
            return response.getCoordinatorProtocolService();
            
        } catch (Event fault) {
        	QName faultCode = fault.getFaultcode();
            //Detail faultDetail = fault.getDetail();
            String faultMessage = fault.getFaultstring();
            //String faultMessage = (faultDetail != null ? faultDetail.getTextContent() : faultstring);
            if (CoordinationConstants.WSCOOR_ERROR_CODE_CANNOT_REGISTER_QNAME.equals(faultCode)) {
                throw new CannotRegisterException(faultMessage) ;
            } else if (CoordinationConstants.WSCOOR_ERROR_CODE_INVALID_PROTOCOL_QNAME.equals(faultCode)) {
                throw new SystemException("Invalid protocol: "+faultMessage) ;
            } else if (CoordinationConstants.WSCOOR_ERROR_CODE_INVALID_STATE_QNAME.equals(faultCode)) {
                throw new SystemException("Invalid state: "+faultMessage);
            }
            
            throw new SystemException("Unknown error: "+faultMessage) ;
        }
    }
}
