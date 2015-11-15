package org.aries.tx.pi.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "ParticipantInstanceService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", 
		wsdlLocation = "/wsdl/wsat-participant-binding.wsdl")
public class ParticipantInstanceService extends Service {

	private final static URL PARTICIPANTINSTANCESERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(ParticipantInstanceService.class.getName());

	static {
		URL url = ParticipantInstanceService.class.getResource("/wsdl/wsat-participant-instance-binding.wsdl");
		PARTICIPANTINSTANCESERVICE_WSDL_LOCATION = url;
	}

	public ParticipantInstanceService() {
		this(PARTICIPANTINSTANCESERVICE_WSDL_LOCATION);
	}

	public ParticipantInstanceService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantInstanceService"));
	}

	public ParticipantInstanceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "ParticipantInstancePortType")
	public ParticipantInstancePortType getParticipantInstancePortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantInstancePortType"), ParticipantInstancePortType.class);
	}

	@WebEndpoint(name = "ParticipantInstancePortType")
	public ParticipantInstancePortType getParticipantInstancePortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantInstancePortType"), ParticipantInstancePortType.class, features);
	}

}
