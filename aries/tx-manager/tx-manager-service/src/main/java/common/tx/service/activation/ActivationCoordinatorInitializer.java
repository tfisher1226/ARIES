package common.tx.service.activation;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


public class ActivationCoordinatorInitializer extends AbstractServiceInitializer {

	public static ActivationCoordinatorInitializer INSTANCE = new ActivationCoordinatorInitializer();
	
	
	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
		ActivationCoordinatorProcessor instance = new ActivationCoordinatorProcessorImpl();
		ActivationCoordinatorProcessor.setCoordinator(instance);

		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Activation Coordination Service");
		descriptor.setServiceName(CoordinationConstants.ACTIVATION_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.ACTIVATION_ENDPOINT_NAME);
		//descriptor.setActionName(CoordinationConstants.ACTIVATION_SERVICE_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wscoor/2006/06");
		descriptor.setImplementationClass(ActivationPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(ActivationPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-service-0.0.1-SNAPSHOT");
		return descriptor;
	}

}
