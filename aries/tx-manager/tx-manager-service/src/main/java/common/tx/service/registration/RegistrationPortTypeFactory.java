package common.tx.service.registration;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.registration.RegistrationService;
import org.aries.tx.service.AbstractPortTypeFactory;


public class RegistrationPortTypeFactory extends AbstractPortTypeFactory {

	private static ThreadLocal<RegistrationService> registrationService = new ThreadLocal<RegistrationService>();

	private static synchronized RegistrationService getRegistrationService() {
		if (registrationService.get() == null)
			registrationService.set(new RegistrationService());
		return registrationService.get();
	}

	public static RegistrationPortType getRegistrationPort() {
		//RegistrationPortType port = getRegistrationService().getPort(RegistrationPortType.class, new AddressingFeature(true, true));
		RegistrationPortType port = getRegistrationService().getPort(RegistrationPortType.class);
		initializePort(port);
		return port;
	}

	public static RegistrationPortType getRegistrationPort(W3CEndpointReference endpointReference, String transactionId, String participantId) {
		//RegistrationPortType port = getRegistrationService().getPort(endpointReference, RegistrationPortType.class, new AddressingFeature(true, true));
		RegistrationPortType port = getRegistrationService().getPort(endpointReference, RegistrationPortType.class);
		initializePort(port, transactionId, participantId, null);
		return port;
	}

	public static void configurePort(Object port) {
//		AddressingEndpoint endpoint = new AddressingEndpoint(port);
//		endpoint.setServiceName(CoordinationConstants.REGISTRATION_SERVICE_NAME);
//		endpoint.setPortTypeName(CoordinationConstants.REGISTRATION_ENDPOINT_NAME);
//		endpoint.setActionName(CoordinationConstants.WSCOOR_ACTION_REGISTER);
//		String messageId = MessageId.getMessageId();
//		applyAddressingHeader(endpoint, messageId);
	}

}
