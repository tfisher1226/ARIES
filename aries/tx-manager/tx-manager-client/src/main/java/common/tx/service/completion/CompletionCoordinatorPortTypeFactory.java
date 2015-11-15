package common.tx.service.completion;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;

import common.tx.InstanceIdentifier;


public class CompletionCoordinatorPortTypeFactory extends AbstractPortTypeFactory {

    private static ThreadLocal<CompletionCoordinatorService> completionCoordinatorService = new ThreadLocal<CompletionCoordinatorService>();

    private static synchronized CompletionCoordinatorService getCompletionCoordinatorService() {
        if (completionCoordinatorService.get() == null)
            completionCoordinatorService.set(new CompletionCoordinatorService());
        return completionCoordinatorService.get();
    }

    public static CompletionCoordinatorPortType getCompletionCoordinatorPort() {
        //CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(CompletionCoordinatorPortType.class, new AddressingFeature(true, true));
        CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(CompletionCoordinatorPortType.class);
		initializePort(port);
        return port;
    }

	public static CompletionCoordinatorPortType getCompletionCoordinatorPort(W3CEndpointReference endpointReference) {
        //CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(endpointReference, CompletionCoordinatorPortType.class, new AddressingFeature(true, true));
        CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(endpointReference, CompletionCoordinatorPortType.class);
		initializePort(port);
        return port;
    }

    public static CompletionCoordinatorPortType getCompletionCoordinatorPort(W3CEndpointReference endpointReference, InstanceIdentifier identifier) {
        //CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(CompletionCoordinatorPortType.class, new AddressingFeature(true, true));
        CompletionCoordinatorPortType port = getCompletionCoordinatorService().getPort(endpointReference, CompletionCoordinatorPortType.class);
		initializePort(port, identifier.getInstanceIdentifier());
        return port;
    }

}
