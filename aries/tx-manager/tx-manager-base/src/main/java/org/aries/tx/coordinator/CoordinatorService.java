package org.aries.tx.coordinator;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(
		name = "CoordinatorService", 
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", 
		wsdlLocation = "/wsdl/wsat-coordinator-binding.wsdl")
public class CoordinatorService extends Service {

	private final static URL COORDINATORSERVICE_WSDL_LOCATION;
	//private final static Logger logger = Logger.getLogger(CoordinatorService.class.getName());

	static {
		URL url = CoordinatorService.class.getResource("/wsdl/wsat-completion-binding.wsdl");
		COORDINATORSERVICE_WSDL_LOCATION = url;
	}

	public CoordinatorService() {
		this(COORDINATORSERVICE_WSDL_LOCATION);
	}

	public CoordinatorService(URL wsdlLocation) {
		super(wsdlLocation, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CoordinatorService"));
	}

	public CoordinatorService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	@WebEndpoint(name = "CoordinatorPortType")
	public CoordinatorPortType getCoordinatorPortType() {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CoordinatorPortType"), CoordinatorPortType.class);
	}

	@WebEndpoint(name = "CoordinatorPortType")
	public CoordinatorPortType getCoordinatorPortType(EndpointReference endpointReference) {
		return super.getPort(endpointReference, CoordinatorPortType.class);
	}
	
	@WebEndpoint(name = "CoordinatorPortType")
	public CoordinatorPortType getCoordinatorPortType(WebServiceFeature... features) {
		return super.getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CoordinatorPortType"), CoordinatorPortType.class, features);
	}

	@WebEndpoint(name = "CoordinatorPortType")
	public CoordinatorPortType getCoordinatorPortType(EndpointReference endpointReference, WebServiceFeature... features) {
		return super.getPort(endpointReference, CoordinatorPortType.class, features);
	}
	
}
