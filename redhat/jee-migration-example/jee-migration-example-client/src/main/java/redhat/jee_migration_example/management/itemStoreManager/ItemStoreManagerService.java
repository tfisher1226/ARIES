package redhat.jee_migration_example.management.itemStoreManager;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "itemStoreManager", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class ItemStoreManagerService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ItemStoreManagerService service;
	
	
	public ItemStoreManagerService() {
		this(WSDL_LOCATION);
	}
	
	public ItemStoreManagerService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "itemStoreManagerService"));
	}
	
	public ItemStoreManagerService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "itemStoreManager")
	public ItemStoreManager getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemStoreManager"), ItemStoreManager.class);
	}
	
	@WebEndpoint(name = "itemStoreManager")
	public ItemStoreManager getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "itemStoreManager"), ItemStoreManager.class, features);
	}
	
}
