package bookshop2.supplier.client.queryBooks;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "queryBooks", targetNamespace = "http://bookshop2/supplier")
public class QueryBooksService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private QueryBooksService service;
	
	
	public QueryBooksService() {
		this(WSDL_LOCATION);
	}
	
	public QueryBooksService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/supplier", "queryBooksService"));
	}
	
	public QueryBooksService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "queryBooks")
	public QueryBooks getPort() {
		return super.getPort(new QName("http://bookshop2/supplier", "queryBooks"), QueryBooks.class);
	}
	
	@WebEndpoint(name = "queryBooks")
	public QueryBooks getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/supplier", "queryBooks"), QueryBooks.class, features);
	}
	
}
