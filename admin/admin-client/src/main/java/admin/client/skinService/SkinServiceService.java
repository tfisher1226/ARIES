package admin.client.skinService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "skinService", targetNamespace = "http://admin")
public class SkinServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private SkinServiceService service;
	
	
	public SkinServiceService() {
		this(WSDL_LOCATION);
	}
	
	public SkinServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://admin", "skinServiceService"));
	}
	
	public SkinServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "skinService")
	public SkinService getPort() {
		return super.getPort(new QName("http://admin", "skinService"), SkinService.class);
	}
	
	@WebEndpoint(name = "skinService")
	public SkinService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://admin", "skinService"), SkinService.class, features);
	}
	
}
