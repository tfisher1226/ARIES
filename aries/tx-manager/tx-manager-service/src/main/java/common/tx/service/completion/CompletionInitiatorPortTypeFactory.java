package common.tx.service.completion;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;

import common.tx.InstanceIdentifier;


public class CompletionInitiatorPortTypeFactory extends AbstractPortTypeFactory {

    private static ThreadLocal<CompletionInitiatorService> completionInitiatorService = new ThreadLocal<CompletionInitiatorService>();

    private static synchronized CompletionInitiatorService getCompletionInitiatorService() {
        if (completionInitiatorService.get() == null)
        	completionInitiatorService.set(new CompletionInitiatorService());
        return completionInitiatorService.get();
    }

    public static CompletionInitiatorPortType getCompletionInitiatorPort() {
        //CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(CompletionInitiatorPortType.class, new AddressingFeature(true, true));
        CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(CompletionInitiatorPortType.class);
        initializePort(port);
        return port;
    }

    public static CompletionInitiatorPortType getCompletionInitiatorPort(W3CEndpointReference endpointReference) {
        //CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(endpointReference, CompletionInitiatorPortType.class, new AddressingFeature(true, true));
        CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(endpointReference, CompletionInitiatorPortType.class);
        initializePort(port);
        return port;
    }

    public static CompletionInitiatorPortType getCompletionInitiatorPort(W3CEndpointReference endpointReference, InstanceIdentifier identifier) {
        //CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(endpointReference, CompletionInitiatorPortType.class, new AddressingFeature(true, true));
        CompletionInitiatorPortType port = getCompletionInitiatorService().getPort(endpointReference, CompletionInitiatorPortType.class);
        initializePort(port, identifier.getInstanceIdentifier());
        return port;
    }

}
