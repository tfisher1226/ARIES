package common.tx.service.completion;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


/**
 * Initialize the Completion Coordinator service.
 */
public class CompletionCoordinatorInitializer extends AbstractServiceInitializer {
	
	public static CompletionCoordinatorInitializer INSTANCE = new CompletionCoordinatorInitializer();
	
	
	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
    	CompletionCoordinatorProcessor instance = new CompletionCoordinatorProcessorImpl();
    	CompletionCoordinatorProcessor.setInstance(instance);
    	
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Completion Coordination Service");
		descriptor.setServiceName(CoordinationConstants.COMPLETION_COORDINATOR_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.COMPLETION_COORDINATOR_ENDPOINT_NAME);
		//descriptor.setActionName(CoordinationConstants.COMPLETION_COORDINATOR_ACTION_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wsat/2006/06");
		descriptor.setImplementationClass(CompletionCoordinatorPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(CompletionCoordinatorPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-service-0.0.1-SNAPSHOT");
		return descriptor;
    }
    
}