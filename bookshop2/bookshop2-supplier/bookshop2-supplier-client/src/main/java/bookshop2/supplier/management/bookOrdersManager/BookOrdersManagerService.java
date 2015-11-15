package bookshop2.supplier.management.bookOrdersManager;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "bookOrdersManager", targetNamespace = "http://bookshop2/supplier")
public class BookOrdersManagerService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private BookOrdersManagerService service;
	
	
	public BookOrdersManagerService() {
		this(WSDL_LOCATION);
	}
	
	public BookOrdersManagerService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "bookOrdersManagerService"));
	}
	
	public BookOrdersManagerService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "bookOrdersManager")
	public BookOrdersManager getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "bookOrdersManager"), BookOrdersManager.class);
	}
	
	@WebEndpoint(name = "bookOrdersManager")
	public BookOrdersManager getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "bookOrdersManager"), BookOrdersManager.class, features);
	}
	
}
