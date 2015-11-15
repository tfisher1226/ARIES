package org.aries.tx.coordinator;

import java.net.URL;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.model.Notification;


public class CoordinatorClient extends AbstractPortTypeClient {

	private static CoordinatorClient INSTANCE = new CoordinatorClient() ;

	public static CoordinatorClient getInstance() {
		return INSTANCE;
	}

	
	private CoordinatorService service;

    private CoordinatorClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.COORDINATOR_SERVICE_NAME);
		service = new CoordinatorService(wsdlLocation);
    }

	public int getStatus(String transactionId) {
        CoordinatorPortType port = service.getCoordinatorPortType();
        initializePort(port, transactionId, null, null);
		int status = port.getStatus(transactionId);
		return status;
	}

	public int getStatus(W3CEndpointReference endpoint, String transactionId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, transactionId, null, null);
		int status = port.getStatus(transactionId);
		return status;
	}
	
	public void sendPrepared(W3CEndpointReference endpoint, String participantId, String coordinatorId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, null, participantId, coordinatorId);
		Notification notification = new Notification();
		port.preparedOperation(notification);
	}

	public void sendReadOnly(W3CEndpointReference endpoint, String participantId, String coordinatorId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, null, participantId, coordinatorId);
		Notification notification = new Notification();
		port.readOnlyOperation(notification);
	}

	public void sendCommitted(W3CEndpointReference endpoint, String participantId, String coordinatorId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, null, participantId, coordinatorId);
		Notification notification = new Notification();
		port.committedOperation(notification);
	}

	public void sendAborted(W3CEndpointReference endpoint, String participantId, String coordinatorId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, null, participantId, coordinatorId);
		Notification notification = new Notification();
		port.abortedOperation(notification);
	}

	public void sendFault(W3CEndpointReference endpoint, Fault fault, String participantId, String coordinatorId) {
        CoordinatorPortType port = service.getCoordinatorPortType(endpoint);
        initializePort(port, null, participantId, coordinatorId);
		// convert fault to wire form and dispatch to initiator
		//fault.setFaultactor(faultAction);
		port.fault(fault);
	}

//	/**
//	 * return a participant endpoint appropriate to the type of coordinator
//	 * @param endpoint
//	 * @return either the secure participant endpoint or the non-secure endpoint
//	 */
//	MAPEndpoint getParticipant(W3CEndpointReference endpoint, MAP map) {
//		String address;
//		if (endpoint != null) {
//			NativeEndpointReference nativeRef = EndpointHelper.transform(NativeEndpointReference.class, endpoint);
//			address = nativeRef.getAddress();
//		} else {
//			address = map.getTo();
//		}
//
//		if (address.startsWith("https")) {
//			return secureParticipant;
//		} else {
//			return participant;
//		}
//	}

}
