package common.tx.service.coordinator;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


/**
 * Initialize the Coordinator service.
 */
public class CoordinatorInitializer extends AbstractServiceInitializer {
	
	public static CoordinatorInitializer INSTANCE = new CoordinatorInitializer();

	
	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
    	CoordinatorProcessor instance = new CoordinatorProcessorImpl();
    	CoordinatorProcessor.setInstance(instance);

		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Coordinator Service");
		descriptor.setServiceName(CoordinationConstants.COORDINATOR_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.COORDINATOR_ENDPOINT_NAME);
		//descriptor.setActionName(CoordinationConstants.ACTIVATION_SERVICE_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wscoor/2006/06");
		descriptor.setImplementationClass(CoordinatorPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(CoordinatorPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-service-0.0.1-SNAPSHOT");
		return descriptor;
    }
    
}