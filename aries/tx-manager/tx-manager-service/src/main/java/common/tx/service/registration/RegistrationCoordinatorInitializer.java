package common.tx.service.registration;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


/**
 * Initialize the Registration Coordinator service
 */
public class RegistrationCoordinatorInitializer extends AbstractServiceInitializer {
	
	public static RegistrationCoordinatorInitializer INSTANCE = new RegistrationCoordinatorInitializer();
	

	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
    	RegistrationCoordinatorProcessor instance = new RegistrationCoordinatorProcessorImpl();
    	RegistrationCoordinatorProcessor.setCoordinator(instance);
		
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Registration Coordination Service");
		descriptor.setServiceName(CoordinationConstants.REGISTRATION_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.REGISTRATION_ENDPOINT_NAME);
		//descriptor.setActionName(CoordinationConstants.REGISTRATION_ACTION_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wscoor/2006/06");
		descriptor.setImplementationClass(RegistrationPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(RegistrationPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-service-0.0.1-SNAPSHOT");
		return descriptor;
    }
    
}
