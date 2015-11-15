package bookshop2.supplier.management.reservedBooksManager;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "reservedBooksManager", targetNamespace = "http://bookshop2/supplier")
public class ReservedBooksManagerService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ReservedBooksManagerService service;
	
	
	public ReservedBooksManagerService() {
		this(WSDL_LOCATION);
	}
	
	public ReservedBooksManagerService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "reservedBooksManagerService"));
	}
	
	public ReservedBooksManagerService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "reservedBooksManager")
	public ReservedBooksManager getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "reservedBooksManager"), ReservedBooksManager.class);
	}
	
	@WebEndpoint(name = "reservedBooksManager")
	public ReservedBooksManager getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "reservedBooksManager"), ReservedBooksManager.class, features);
	}
	
}
