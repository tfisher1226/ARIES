package admin.client.registrationService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "registrationService", targetNamespace = "http://admin")
public class RegistrationServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private RegistrationServiceService service;
	
	
	public RegistrationServiceService() {
		this(WSDL_LOCATION);
	}
	
	public RegistrationServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://admin", "registrationServiceService"));
	}
	
	public RegistrationServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "registrationService")
	public RegistrationService getPort() {
		return super.getPort(new QName("http://admin", "registrationService"), RegistrationService.class);
	}
	
	@WebEndpoint(name = "registrationService")
	public RegistrationService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://admin", "registrationService"), RegistrationService.class, features);
	}
	
}
