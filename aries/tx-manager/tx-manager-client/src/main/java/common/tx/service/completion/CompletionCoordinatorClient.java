package common.tx.service.completion;

import java.io.IOException;
import java.net.URL;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;

import common.tx.CoordinationConstants;
import common.tx.model.Event;
import common.tx.model.Notification;


public class CompletionCoordinatorClient extends AbstractPortTypeClient {

	private static CompletionCoordinatorClient CLIENT = new CompletionCoordinatorClient() ;

	public static CompletionCoordinatorClient getClient() {
		return CLIENT;
	}


	private CompletionCoordinatorService service;

    private CompletionCoordinatorClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.COMPLETION_COORDINATOR_SERVICE_NAME);
		service = new CompletionCoordinatorService(wsdlLocation);
    }

	public void sendCommit(W3CEndpointReference endpoint, String transactionId) throws Event, IOException {
        CompletionCoordinatorPortType port = service.getCompletionCoordinatorPortType(endpoint);
		initializePort(port, transactionId);
		Notification notification = new Notification();
		port.commitOperation(notification);
	}

	public void sendRollback(W3CEndpointReference endpoint, String transactionId) throws Event, IOException {
        CompletionCoordinatorPortType port = service.getCompletionCoordinatorPortType(endpoint);
		initializePort(port, transactionId);
		Notification notification = new Notification();
		port.rollbackOperation(notification);
	}

}
