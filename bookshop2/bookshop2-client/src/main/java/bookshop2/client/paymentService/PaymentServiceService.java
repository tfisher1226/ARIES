package bookshop2.client.paymentService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "paymentService", targetNamespace = "http://bookshop2")
public class PaymentServiceService extends Service {
	
	private static URL WSDL_LOCATION;
	
	private PaymentServiceService service;
	
	
	public PaymentServiceService() {
		this(WSDL_LOCATION);
	}
	
	public PaymentServiceService(URL wsdlLocation) {
		this(wsdlLocation, new QName("http://bookshop2", "paymentServiceService"));
	}
	
	public PaymentServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}
	
	
	@WebEndpoint(name = "paymentService")
	public PaymentService getPort() {
		return super.getPort(new QName("http://bookshop2", "paymentService"), PaymentService.class);
	}
	
	@WebEndpoint(name = "paymentService")
	public PaymentService getPort(WebServiceFeature... features) {
		return super.getPort(new QName("http://bookshop2", "paymentService"), PaymentService.class, features);
	}
	
}
