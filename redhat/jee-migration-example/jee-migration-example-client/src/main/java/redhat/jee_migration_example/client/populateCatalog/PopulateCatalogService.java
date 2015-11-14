package redhat.jee_migration_example.client.populateCatalog;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "populateCatalog", targetNamespace = "http://www.redhat.com/jee-migration-example")
public class PopulateCatalogService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private PopulateCatalogService service;
	
	
	public PopulateCatalogService() {
		this(WSDL_LOCATION);
	}
	
	public PopulateCatalogService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://www.redhat.com/jee-migration-example", "populateCatalogService"));
	}
	
	public PopulateCatalogService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "populateCatalog")
	public PopulateCatalog getPort() {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "populateCatalog"), PopulateCatalog.class);
	}
	
	@WebEndpoint(name = "populateCatalog")
	public PopulateCatalog getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://www.redhat.com/jee-migration-example", "populateCatalog"), PopulateCatalog.class, features);
	}
	
}
