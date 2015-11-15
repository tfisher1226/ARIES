package admin.client.roleService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "roleService", targetNamespace = "http://admin")
public class RoleServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private RoleServiceService service;
	
	
	public RoleServiceService() {
		this(WSDL_LOCATION);
	}
	
	public RoleServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://admin", "roleServiceService"));
	}
	
	public RoleServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "roleService")
	public RoleService getPort() {
		return super.getPort(new QName("http://admin", "roleService"), RoleService.class);
	}
	
	@WebEndpoint(name = "roleService")
	public RoleService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://admin", "roleService"), RoleService.class, features);
	}
	
}
