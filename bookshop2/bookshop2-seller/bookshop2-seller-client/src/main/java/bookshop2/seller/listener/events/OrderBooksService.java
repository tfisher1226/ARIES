package bookshop2.seller.listener.events;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "orderBooks", targetNamespace = "http://bookshop2/seller")
public class OrderBooksService extends Service {

	private static URL WSDL_LOCATION;

	private OrderBooksService service;


	public OrderBooksService() {
		this(WSDL_LOCATION);
	}

	public OrderBooksService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2/seller", "orderBooksService"));
	}

	public OrderBooksService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}


	@WebEndpoint(name = "orderBooks")
	public OrderBooks getPort() {
		return super.getPort(new QName("http://bookshop2/seller", "orderBooks"), OrderBooks.class);
	}

	@WebEndpoint(name = "orderBooks")
	public OrderBooks getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2/seller", "orderBooks"), OrderBooks.class, features);
	}

}
