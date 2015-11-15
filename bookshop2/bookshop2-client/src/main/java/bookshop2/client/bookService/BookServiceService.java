package bookshop2.client.bookService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "bookService", targetNamespace = "http://bookshop2")
public class BookServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private BookServiceService service;
	
	
	public BookServiceService() {
		this(WSDL_LOCATION);
	}
	
	public BookServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "bookServiceService"));
	}
	
	public BookServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "bookService")
	public BookService getPort() {
		return super.getPort(new QName("http://bookshop2", "bookService"), BookService.class);
	}
	
	@WebEndpoint(name = "bookService")
	public BookService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "bookService"), BookService.class, features);
	}
	
}
