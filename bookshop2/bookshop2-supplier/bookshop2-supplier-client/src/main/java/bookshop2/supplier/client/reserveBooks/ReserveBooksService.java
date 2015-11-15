package bookshop2.supplier.client.reserveBooks;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "reserveBooks", targetNamespace = "http://bookshop2/supplier")
public class ReserveBooksService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private ReserveBooksService service;
	
	
	public ReserveBooksService() {
		this(WSDL_LOCATION);
	}
	
	public ReserveBooksService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "reserveBooksService"));
	}
	
	public ReserveBooksService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "reserveBooks")
	public ReserveBooks getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "reserveBooks"), ReserveBooks.class);
	}
	
	@WebEndpoint(name = "reserveBooks")
	public ReserveBooks getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "reserveBooks"), ReserveBooks.class, features);
	}
	
}
