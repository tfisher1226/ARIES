package common.tx.service.participant;

import org.aries.tx.participant.ParticipantProcessor;
import org.aries.tx.participant.ParticipantProcessorImpl;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;

import common.tx.CoordinationConstants;


public class ParticipantInitializer extends AbstractServiceInitializer {

	public static ParticipantInitializer INSTANCE = new ParticipantInitializer();
	

	public void initialize(String host, int port) {
		initialize(host, port, true);
	}

	public void initialize(String host, int port, boolean launch) {
		EndpointDescriptor descriptor = createEndpointDescriptor(host, port);
		initialize(descriptor, launch);
	}

	protected EndpointDescriptor createEndpointDescriptor(String host, int port) {
		ParticipantProcessor instance = new ParticipantProcessorImpl();
		ParticipantProcessor.setInstance(instance);

		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Participant Service");
		descriptor.setServiceName(CoordinationConstants.PARTICIPANT_SERVICE_NAME);
		descriptor.setPortTypeName(CoordinationConstants.PARTICIPANT_ENDPOINT_NAME);
		descriptor.setNamespace("http://docs.oasis-open.org/ws-tx/wsat/2006/06");
		descriptor.setImplementationClass(ParticipantPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(ParticipantPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-manager-service-0.0.1-SNAPSHOT");
		return descriptor;
    }

}
