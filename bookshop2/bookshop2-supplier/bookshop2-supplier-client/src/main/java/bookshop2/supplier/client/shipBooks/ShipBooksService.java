package bookshop2.supplier.client.shipBooks;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "shipBooks", targetNamespace = "http://bookshop2/supplier")
public class ShipBooksService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ShipBooksService service;
	
	
	public ShipBooksService() {
		this(WSDL_LOCATION);
	}
	
	public ShipBooksService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "shipBooksService"));
	}
	
	public ShipBooksService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "shipBooks")
	public ShipBooks getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "shipBooks"), ShipBooks.class);
	}
	
	@WebEndpoint(name = "shipBooks")
	public ShipBooks getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "shipBooks"), ShipBooks.class, features);
	}
	
}
