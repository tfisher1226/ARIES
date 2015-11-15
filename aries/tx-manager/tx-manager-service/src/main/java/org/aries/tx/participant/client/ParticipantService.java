package org.aries.tx.participant.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "ParticipantService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", 
		wsdlLocation = "/wsdl/wsat-participant-binding.wsdl")
public class ParticipantService extends Service {

	private final static URL PARTICIPANTSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(ParticipantService.class.getName());

	static {
		URL url = ParticipantService.class.getResource("/wsdl/wsat-participant-binding.wsdl");
		PARTICIPANTSERVICE_WSDL_LOCATION = url;
	}

	public ParticipantService() {
		this(PARTICIPANTSERVICE_WSDL_LOCATION);
	}

	public ParticipantService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantService"));
	}

	public ParticipantService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "ParticipantPortType")
	public ParticipantPortType getParticipantPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantPortType"), ParticipantPortType.class);
	}

	@WebEndpoint(name = "ParticipantPortType")
	public ParticipantPortType getParticipantPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantPortType"), ParticipantPortType.class, features);
	}

}
