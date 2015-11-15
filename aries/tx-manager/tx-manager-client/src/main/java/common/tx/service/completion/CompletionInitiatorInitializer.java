package common.tx.service.completion;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


/**
 * Initialize the Completion Initiator service
 */
public class CompletionInitiatorInitializer extends AbstractServiceInitializer {

	public static CompletionInitiatorInitializer INSTANCE = new CompletionInitiatorInitializer();
	

	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
    	CompletionInitiatorProcessor instance = new CompletionInitiatorProcessorImpl();
    	CompletionInitiatorProcessorImpl.setInstance(instance);
    	
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Completion Initiator Service");
		descriptor.setServiceName(CoordinationConstants.COMPLETION_INITIATOR_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.COMPLETION_INITIATOR_ENDPOINT_NAME);
		//descriptor.setActionName(CoordinationConstants.COMPLETION_INITIATOR_ACTION_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wsat/2006/06");
		descriptor.setImplementationClass(CompletionInitiatorPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(CompletionInitiatorPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-client-0.0.1-SNAPSHOT");
		return descriptor;
    }
    
}