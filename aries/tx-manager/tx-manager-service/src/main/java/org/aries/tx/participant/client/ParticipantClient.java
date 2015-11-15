package org.aries.tx.participant.client;

import java.net.URL;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.model.Notification;


public class ParticipantClient extends AbstractPortTypeClient {

	private static ParticipantClient INSTANCE = new ParticipantClient();

	public static ParticipantClient getInstance() {
		return INSTANCE;
	}
	
	
	private ParticipantService service;

    private ParticipantClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.PARTICIPANT_SERVICE_NAME);
		service = new ParticipantService(wsdlLocation);
    }

    
    public void sendPrepare(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantPortType port = service.getPort(endpoint, ParticipantPortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.prepareOperation(notification);
    }

	public void sendCommit(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantPortType port = service.getPort(endpoint, ParticipantPortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.commitOperation(notification);
    }

    public void sendRollback(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantPortType port = service.getPort(endpoint, ParticipantPortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.rollbackOperation(notification);
    }

    public void sendFault(String transactionId, String coordinatorId, String participantId, Fault fault) {
        ParticipantPortType port = service.getPort(ParticipantPortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
        port.fault(fault);
    }
    
}
