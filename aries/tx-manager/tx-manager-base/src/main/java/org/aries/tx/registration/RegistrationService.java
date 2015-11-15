package org.aries.tx.registration;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "RegistrationService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", 
		wsdlLocation = "/wsdl/wscoor-registration-binding.wsdl")
public class RegistrationService extends Service {

	private final static URL REGISTRATIONSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(RegistrationService.class.getName());

	static {
		URL url = RegistrationService.class.getResource("/wsdl/wscoor-registration-binding.wsdl");
		REGISTRATIONSERVICE_WSDL_LOCATION = url;
	}

	public RegistrationService() {
		this(REGISTRATIONSERVICE_WSDL_LOCATION);
	}

	public RegistrationService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationService"));
	}

	public RegistrationService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	@WebEndpoint(name = "RegistrationPortType")
	public RegistrationPortType getRegistrationPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationPortType"), RegistrationPortType.class);
	}

	@WebEndpoint(name = "RegistrationPortType")
	public RegistrationPortType getRegistrationPortType(EndpointReference endpointReference) {
		return super.getPort(endpointReference, RegistrationPortType.class);
	}
	
	@WebEndpoint(name = "RegistrationPortType")
	public RegistrationPortType getRegistrationPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationPortType"), RegistrationPortType.class, features);
	}

	@WebEndpoint(name = "RegistrationPortType")
	public RegistrationPortType getRegistrationPortType(EndpointReference endpointReference, WebServiceFeature... features) {
		return super.getPort(endpointReference, RegistrationPortType.class, features);
	}
	

}
