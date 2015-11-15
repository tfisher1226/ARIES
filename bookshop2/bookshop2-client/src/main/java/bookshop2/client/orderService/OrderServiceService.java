package bookshop2.client.orderService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "orderService", targetNamespace = "http://bookshop2")
public class OrderServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private OrderServiceService service;
	
	
	public OrderServiceService() {
		this(WSDL_LOCATION);
	}
	
	public OrderServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "orderServiceService"));
	}
	
	public OrderServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "orderService")
	public OrderService getPort() {
		return super.getPort(new QName("http://bookshop2", "orderService"), OrderService.class);
	}
	
	@WebEndpoint(name = "orderService")
	public OrderService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "orderService"), OrderService.class, features);
	}
	
}
