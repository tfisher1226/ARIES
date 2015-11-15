package common.tx.service.completion;

import java.net.URL;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.client.AbstractPortTypeClient;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.CoordinationConstants;
import common.tx.model.Notification;


public class CompletionInitiatorClient extends AbstractPortTypeClient {
	
    private static final CompletionInitiatorClient CLIENT = new CompletionInitiatorClient() ;

    public static CompletionInitiatorClient getClient() {
        return CLIENT;
    }

//    private String committedAction = null;
//
//    private String abortedAction = null;
//
//    private String faultAction = null;
//
//    private MAPEndpoint completionCoordinator ;
//
//    private MAPEndpoint secureCompletionCoordinator ;

	private CompletionInitiatorService service;


    private CompletionInitiatorClient() {
		URL wsdlLocation = getWsdlLocation(CoordinationConstants.COMPLETION_INITIATOR_SERVICE_NAME);
		service = new CompletionInitiatorService(wsdlLocation);
    }

//        final MAPBuilder builder = MAPBuilderFactory.getInstance().getBuilderInstance();
//        committedAction = AtomicTransactionConstants.WSAT_ACTION_COMMITTED;
//        abortedAction = AtomicTransactionConstants.WSAT_ACTION_ABORTED;
//        faultAction = AtomicTransactionConstants.WSAT_ACTION_FAULT;
//        final String completionCoordinatorURIString = ServiceRegistry.getRegistry().getServiceURI(AtomicTransactionConstants.COMPLETION_COORDINATOR_SERVICE_NAME, false) ;
//        final String secureCompletionCoordinatorURIString = ServiceRegistry.getRegistry().getServiceURI(AtomicTransactionConstants.COMPLETION_COORDINATOR_SERVICE_NAME, true) ;
//        completionCoordinator = builder.newEndpoint(completionCoordinatorURIString);
//        secureCompletionCoordinator = builder.newEndpoint(secureCompletionCoordinatorURIString);

//	public CompletionInitiatorClient(String host, int port) {
//    	try {
//    		URL wsdlLocation = new URL(getURI(host, port));
//        	restaurantService = new RestaurantService(wsdlLocation);
//    	} catch (Exception e) {
//    		log.error("Error", e);
//    	}
//	}
	
//    private String getURI(String host, int port) throws MalformedURLException {
//		return "http://"+host+":"+port+"/tx-service/RestaurantService/RestaurantPortType?wsdl";
//	}
    
    public void sendCommitted(W3CEndpointReference endpoint, String transactionId) {
        CompletionInitiatorPortType port = service.getCompletionInitiatorPortType(endpoint);
        initializePort(port, transactionId);
        Notification notification = new Notification();
        port.committedOperation(notification);
    }

	public void sendAborted(W3CEndpointReference endpoint, String transactionId) {
        CompletionInitiatorPortType port = service.getCompletionInitiatorPortType(endpoint);
        initializePort(port, transactionId);
        Notification notification = new Notification();
        port.abortedOperation(notification);
    }

    public void sendFault(W3CEndpointReference endpoint, Fault fault, String transactionId) {
        CompletionInitiatorPortType port = service.getCompletionInitiatorPortType(endpoint);
        port.fault(fault);
    }

    public void sendFault(Fault fault, String transactionId) {
        CompletionInitiatorPortType port = service.getCompletionInitiatorPortType();
        port.fault(fault);
    }

//    /**
//     * return a completion coordinator endpoint appropriate to the type of completion initiator
//     * @param participant
//     * @return either the secure terminaton participant endpoint or the non-secure endpoint
//     */
//    MAPEndpoint getCompletionCoordinator(W3CEndpointReference participant)
//    {
//        NativeEndpointReference nativeRef = EndpointHelper.transform(NativeEndpointReference.class, participant);
//        String address = nativeRef.getAddress();
//        if (address.startsWith("https")) {
//            return secureCompletionCoordinator;
//        } else {
//            return completionCoordinator;
//        }
//    }

}
