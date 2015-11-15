package org.aries.tx.pi.client;

import java.net.URL;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.model.Notification;


public class ParticipantInstanceClient extends AbstractPortTypeClient {

	private static ParticipantInstanceClient INSTANCE = new ParticipantInstanceClient();

	public static ParticipantInstanceClient getInstance() {
		return INSTANCE;
	}
	
	
	private ParticipantInstanceService service;

    private ParticipantInstanceClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.PARTICIPANT_INSTANCE_SERVICE_NAME);
		service = new ParticipantInstanceService(wsdlLocation);
    }

    
    public void sendPrepare(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantInstancePortType port = service.getPort(endpoint, ParticipantInstancePortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.prepareOperation(notification);
    }

	public void sendCommit(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantInstancePortType port = service.getPort(endpoint, ParticipantInstancePortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.commitOperation(notification);
    }

    public void sendRollback(String transactionId, String coordinatorId, String participantId, W3CEndpointReference endpoint) {
        ParticipantInstancePortType port = service.getPort(endpoint, ParticipantInstancePortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
		Notification notification = new Notification();
		port.rollbackOperation(notification);
    }

    public void sendFault(String transactionId, String coordinatorId, String participantId, Fault fault) {
        ParticipantInstancePortType port = service.getPort(ParticipantInstancePortType.class);
        initializePort(port, transactionId, participantId, coordinatorId);
        port.fault(fault);
    }
    
}
