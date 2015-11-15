package admin.client.userService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "userService", targetNamespace = "http://admin")
public class UserServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private UserServiceService service;
	
	
	public UserServiceService() {
		this(WSDL_LOCATION);
	}
	
	public UserServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://admin", "userServiceService"));
	}
	
	public UserServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "userService")
	public UserService getPort() {
		return super.getPort(new QName("http://admin", "userService"), UserService.class);
	}
	
	@WebEndpoint(name = "userService")
	public UserService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://admin", "userService"), UserService.class, features);
	}
	
}
