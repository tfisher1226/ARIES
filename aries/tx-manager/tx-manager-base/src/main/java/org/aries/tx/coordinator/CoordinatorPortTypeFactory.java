package org.aries.tx.coordinator;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;


public class CoordinatorPortTypeFactory extends AbstractPortTypeFactory {

    private static ThreadLocal<CoordinatorService> coordinatorService = new ThreadLocal<CoordinatorService>();

    private static synchronized CoordinatorService getCoordinatorService() {
        if (coordinatorService.get() == null)
            coordinatorService.set(new CoordinatorService());
        return coordinatorService.get();
    }

    public static CoordinatorPortType getCoordinatorPort() {
        //CoordinatorPortType port = getCoordinatorService().getPort(CoordinatorPortType.class, new AddressingFeature(true, true));
        CoordinatorPortType port = getCoordinatorService().getPort(CoordinatorPortType.class);
        initializePort(port);
        return port;
    }
    
    public static CoordinatorPortType getCoordinatorPort(W3CEndpointReference endpointReference) {
        //CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class, new AddressingFeature(true, true));
        CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class);
        initializePort(port);
        return port;
    }

    public static CoordinatorPortType getCoordinatorPort(W3CEndpointReference endpointReference, String coordinatorId) {
        //CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class, new AddressingFeature(true, true));
        CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class);
        initializePort(port, null, null, coordinatorId);
        return port;
    }

    public static CoordinatorPortType getCoordinatorPort(W3CEndpointReference endpointReference, String participantId, String coordinatorId) {
        //CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class, new AddressingFeature(true, true));
        CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class);
        initializePort(port, null, participantId, coordinatorId);
        return port;
    }

//    public static CoordinatorPortType getCoordinatorPort(W3CEndpointReference endpointReference, InstanceIdentifier instanceIdentifier) {
//        //CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class, new AddressingFeature(true, true));
//        CoordinatorPortType port = getCoordinatorService().getPort(endpointReference, CoordinatorPortType.class);
//        initializePort(port, instanceIdentifier.getInstanceIdentifier());
//        return port;
//    }

}
