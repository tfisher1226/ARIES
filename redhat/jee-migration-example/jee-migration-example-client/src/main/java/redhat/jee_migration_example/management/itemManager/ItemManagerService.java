package redhat.jee_migration_example.management.itemManager;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "itemManager", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class ItemManagerService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ItemManagerService service;
	
	
	public ItemManagerService() {
		this(WSDL_LOCATION);
	}
	
	public ItemManagerService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "itemManagerService"));
	}
	
	public ItemManagerService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "itemManager")
	public ItemManager getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemManager"), ItemManager.class);
	}
	
	@WebEndpoint(name = "itemManager")
	public ItemManager getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemManager"), ItemManager.class, features);
	}
	
}
