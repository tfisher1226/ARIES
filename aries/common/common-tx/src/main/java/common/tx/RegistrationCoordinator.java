package common.tx;

import javax.transaction.SystemException;

import common.tx.model.Fault;
import common.tx.model.context.CoordinationContextType;


/**
 * Wrapper around low level Registration Coordinator messaging.
 */
public class RegistrationCoordinator {

	/**
	 * Register the participant in the protocol.
	 * @param coordinationContext The current coordination context
	 * @param messageID The messageID to use.
	 * @param protocol 
	 * @param participant 
	 * @param participantProtocolService The participant protocol service.
	 * @param protocolIdentifier The protocol identifier.
	 * @return The endpoint reference of the coordinator protocol service.
	 * @throws AlreadyRegisteredException If the participant is already registered.
	 * @throws InvalidProtocolException If the protocol is unsupported.
	 * @throws InvalidStateException If the state is invalid
	 * @throws NoActivityException If there is to activity context active.
	 * @throws Fault for errors during processing.
	 */
	//public static W3CEndpointReference register(CoordinationContextType coordinationContext, String messageID, W3CEndpointReference participantEndpoint, String protocolIdentifier) throws CannotRegisterException, InvalidProtocolException, InvalidStateException, Fault {
	public static void register(CoordinationContextType coordinationContext, String messageID, String protocolIdentifier) throws SystemException {

//		W3CEndpointReference endpointReference = coordinationContext.getRegistrationService();
//		try {
//			RegisterType registerType = new RegisterType();
//			RegisterResponseType response;
//			registerType.setProtocolIdentifier(protocolIdentifier);
//			registerType.setParticipantProtocolService(participantEndpoint);
//			RegistrationPortType port = WSCOORClient.getRegistrationPort(endpointReference, CoordinationConstants.WSCOOR_ACTION_REGISTER, messageID);
//			response = port.registerOperation(registerType);
//			return response.getCoordinatorProtocolService();
//
//		} catch (Exception e) {
//			throw new Fault(e);
//		}
	}
	
}
