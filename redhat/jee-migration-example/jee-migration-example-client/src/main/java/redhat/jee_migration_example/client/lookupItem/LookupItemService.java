package redhat.jee_migration_example.client.lookupItem;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "lookupItem", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class LookupItemService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private LookupItemService service;
	
	
	public LookupItemService() {
		this(WSDL_LOCATION);
	}
	
	public LookupItemService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "lookupItemService"));
	}
	
	public LookupItemService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "lookupItem")
	public LookupItem getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "lookupItem"), LookupItem.class);
	}
	
	@WebEndpoint(name = "lookupItem")
	public LookupItem getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "lookupItem"), LookupItem.class, features);
	}
	
}
